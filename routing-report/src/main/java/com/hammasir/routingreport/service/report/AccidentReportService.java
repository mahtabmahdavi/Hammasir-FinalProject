package com.hammasir.routingreport.service.report;

import com.hammasir.routingreport.component.GeometryHandler;
import com.hammasir.routingreport.model.DTO.ChangeDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import com.hammasir.routingreport.model.DTO.TimeDTO;
import com.hammasir.routingreport.model.entity.report.AccidentReport;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.model.enumuration.Accident;
import com.hammasir.routingreport.repository.AccidentRepository;
import com.hammasir.routingreport.service.AuthenticationService;
import com.hammasir.routingreport.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccidentReportService implements ReportService {

    private final AccidentRepository accidentRepository;
    private final AuthenticationService authenticationService;
    private final GeometryHandler geometryHandler;
    private final RedissonClient redissonClient;

    public ReportDTO convertToReportDto(AccidentReport report) {
        return ReportDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryHandler.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    @Override
    public ReportDTO createReport(ReportDTO report) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String lockKey = report.getType() + "_report_creation_lock_" + user.getId();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean isLocked = lock.tryLock(60, TimeUnit.SECONDS);
            if (isLocked) {
                boolean isExisted = accidentRepository.existsByLocationAndExpirationTime(report.getLocation());
                if (!isExisted) {
                    AccidentReport newReport = new AccidentReport();
                    newReport.setType(report.getType());
                    newReport.setIsApproved(true);
                    newReport.setDuration(1);
                    newReport.setCreationTime(LocalDateTime.now());
                    newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
                    newReport.setLocation(geometryHandler.createGeometry(report.getLocation()));
                    newReport.setCategory(Accident.fromValue(report.getCategory()));
                    newReport.setContributors(List.of());
                    newReport.setUser(authenticationService.findUser(report.getUsername()));
                    return convertToReportDto(accidentRepository.save(newReport));
                } else {
                    throw new IllegalArgumentException("This report is already existed!");
                }
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<ReportDTO> getActiveReports(String location) {
        List<AccidentReport> activeReports = accidentRepository.findByLocationAndExpirationTimeAndIsApproved(geometryHandler.createGeometry(location));
        return activeReports.stream()
                .map(this::convertToReportDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO likeReport(ChangeDTO likedReport) {
        AccidentReport report = accidentRepository.findById(likedReport.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report is NOT found!"));
        List<Long> contributors = report.getContributors();
        User desiredUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (contributors.contains(desiredUser.getId())) {
            throw new IllegalArgumentException("User has already liked or disliked this report.");
        } else {
            contributors.add(desiredUser.getId());
            report.setContributors(contributors);
            Duration durationToAdd = likedReport.isStatus() ? Duration.ofMinutes(6) : Duration.ofMinutes(-6);
            report.setExpirationTime(report.getExpirationTime().plus(durationToAdd));
            return convertToReportDto(accidentRepository.save(report));
        }
    }

    @Override
    public ReportDTO approveReport(ChangeDTO changedReport) {
        throw new IllegalArgumentException("Approval is not supported for this type of report.");
    }

    public TimeDTO getMostAccidentalHour() {
        return TimeDTO.builder()
                .hour(accidentRepository.findMostAccidentalHour())
                .build();
    }
}
