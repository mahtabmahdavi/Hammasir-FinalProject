package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enumuration.Traffic;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "traffic_reports")
public class TrafficReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Traffic category;
}
