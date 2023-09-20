package com.hammasir.routingreport.model.entity.report;

import com.hammasir.routingreport.model.entity.Report;
import com.hammasir.routingreport.model.enumuration.Bug;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bug_reports")
public class BugReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Bug category;
}
