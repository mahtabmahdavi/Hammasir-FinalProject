package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.RoadLocation;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "road_location_reports")
public class RoadLocationReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private RoadLocation roadLocationCategory;
}
