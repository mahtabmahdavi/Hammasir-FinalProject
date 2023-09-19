package com.hammasir.routingreport.model.dto;

import com.hammasir.routingreport.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private String type;
    private String category;
    private String location;
    private int like;
    private String username;
}
