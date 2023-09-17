package com.hammasir.routingreport.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportDto {
    private String type;
    private String category;
    private String location;
    private long userId;
//    private String username;
}
