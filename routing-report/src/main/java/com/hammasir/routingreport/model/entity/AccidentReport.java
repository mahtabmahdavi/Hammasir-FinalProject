package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.Accident;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accident_reports")
public class AccidentReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Accident category;
}
