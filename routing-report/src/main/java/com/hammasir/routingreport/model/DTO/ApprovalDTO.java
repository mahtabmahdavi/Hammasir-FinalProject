package com.hammasir.routingreport.model.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDTO {
    private String type;
    private long reportId;
}
