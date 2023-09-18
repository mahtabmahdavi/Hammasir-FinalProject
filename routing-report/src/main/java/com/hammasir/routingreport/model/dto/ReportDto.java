package com.hammasir.routingreport.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDto {
    private String type;
    private String category;
    private String location;
    private long userId;
//    private String username;
}
