package com.hammasir.routingreport.model.entity.report;

import com.hammasir.routingreport.model.entity.Report;
import com.hammasir.routingreport.model.enumuration.Camera;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "camera_reports")
public class CameraReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Camera category;
}
