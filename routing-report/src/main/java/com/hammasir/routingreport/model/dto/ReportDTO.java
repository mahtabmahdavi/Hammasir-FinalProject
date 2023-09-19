package com.hammasir.routingreport.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private String type;
    private String category;
    private String location;
    private int like;
    private String username;
}
