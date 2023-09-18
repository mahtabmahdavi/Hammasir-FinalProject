package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.Police;
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
