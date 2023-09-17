package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.enums.Bug;
import com.hammasir.routingreport.repository.BugRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class BugService {

    private final BugRepository bugRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public BugService(BugRepository bugRepository, GeometryFactory geometryFactory,
                      UserFactory userFactory) {
        this.bugRepository = bugRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createBugReport(ReportDto report) {
        Optional<BugReport> desiredReport = bugRepository.findByLocationAndExpirationTime(
                report.getLocation(), LocalDateTime.now());
        if (desiredReport.isEmpty()) {
            BugReport newReport = new BugReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusMonths(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(userFactory.findUser(report));
            switch (report.getCategory()) {
                case "NO_ENTRY" -> newReport.setCategory(Bug.NO_ENTRY);
                case "DEAD_END" -> newReport.setCategory(Bug.DEAD_END);
                case "DIRT_ROAD" -> newReport.setCategory(Bug.DIRT_ROAD);
                case "NO_CAR_PATH" -> newReport.setCategory(Bug.NO_CAR_PATH);
                case "FLOW_DIRECTION" -> newReport.setCategory(Bug.FLOW_DIRECTION);
                case "OTHER" -> newReport.setCategory(Bug.OTHER);
                default -> throw new IllegalArgumentException("Category of bug report is NOT valid!");
            }
            bugRepository.save(newReport);
            return ReportDto.builder()
                    .category(newReport.getCategory().name())
                    .location(geometryFactory.createWkt(newReport.getLocation()))
                    .type(newReport.getType())
                    .userId(newReport.getUser().getId())
                    .build();
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }
}
