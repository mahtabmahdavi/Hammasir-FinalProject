package com.hammasir.routingreport.model.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreationDTO {
    private String type;
    private String category;
    private String location;
    private String username;
}
