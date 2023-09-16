package com.hammasir.routingreport.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private String type;
    private String category;
    private String location;
    private String username;
}
