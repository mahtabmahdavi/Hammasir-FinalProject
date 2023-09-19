package com.hammasir.routingreport.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedDTO {
    private String type;
    private long reportId;
}
