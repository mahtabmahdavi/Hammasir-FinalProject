package com.hammasir.routingreport.model.entity.report;

import com.hammasir.routingreport.model.entity.Report;
import com.hammasir.routingreport.model.enumuration.Police;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "police_reports")
public class PoliceReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Police category;
}
