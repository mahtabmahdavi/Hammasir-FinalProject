package com.hammasir.routingreport.service.report;

import com.hammasir.routingreport.component.GeometryHandler;
import com.hammasir.routingreport.model.DTO.ChangeDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import com.hammasir.routingreport.model.entity.report.EventReport;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.model.enumuration.Event;
import com.hammasir.routingreport.repository.EventRepository;
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
public class EventReportService implements ReportService {

    private final EventRepository eventRepository;
    private final AuthenticationService authenticationService;
    private final GeometryHandler geometryHandler;
    private final RedissonClient redissonClient;

    public ReportDTO convertToReportDto(EventReport report) {
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
                boolean isExisted = eventRepository.existsByLocationAndExpirationTime(report.getLocation());
                if (!isExisted) {
                    EventReport newReport = new EventReport();
                    newReport.setType(report.getType());
                    newReport.setIsApproved(true);
                    newReport.setDuration(1);
                    newReport.setCreationTime(LocalDateTime.now());
                    newReport.setExpirationTime(LocalDateTime.now().plusDays(newReport.getDuration()));
                    newReport.setLocation(geometryHandler.createGeometry(report.getLocation()));
                    newReport.setCategory(Event.fromValue(report.getCategory()));
                    newReport.setContributors(List.of());
                    newReport.setUser(authenticationService.findUser(report.getUsername()));
                    return convertToReportDto(eventRepository.save(newReport));
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
        List<EventReport> activeReports = eventRepository.findByLocationAndExpirationTimeAndIsApproved(geometryHandler.createGeometry(location));
        return activeReports.stream()
                .map(this::convertToReportDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO likeReport(ChangeDTO likedReport) {
        EventReport report = eventRepository.findById(likedReport.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report is NOT found!"));
        List<Long> contributors = report.getContributors();
        User desiredUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (contributors.contains(desiredUser.getId())) {
            throw new IllegalArgumentException("User has already liked or disliked this report.");
        } else {
            contributors.add(desiredUser.getId());
            report.setContributors(contributors);
            Duration durationToAdd = likedReport.isStatus() ? Duration.ofHours(2) : Duration.ofHours(-2);
            report.setExpirationTime(report.getExpirationTime().plus(durationToAdd));
            return convertToReportDto(eventRepository.save(report));
        }
    }

    @Override
    public ReportDTO approveReport(ChangeDTO changedReport) {
        throw new IllegalArgumentException("Approval is not supported for this type of report.");
    }
}
