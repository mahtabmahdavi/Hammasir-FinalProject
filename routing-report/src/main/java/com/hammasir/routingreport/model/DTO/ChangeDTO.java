package com.hammasir.routingreport.model.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeDTO {
    private String type;
    private long reportId;
    private boolean status;
}
