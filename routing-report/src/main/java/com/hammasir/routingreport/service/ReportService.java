package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.ApplicationHandler;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ApplicationHandler handler;

    public ReportDto createReport(ReportDto reportDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reportDto.setUsername(user.getUsername());
        return handler.createReport(reportDto);
    }

    public List<ReportDto> getActiveReport(String location) {
        return handler.getActiveReport(location);
    }
}
