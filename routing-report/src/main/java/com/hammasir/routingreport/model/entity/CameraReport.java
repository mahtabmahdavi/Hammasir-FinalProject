package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.Camera;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "camera_reports")
public class CameraReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Camera cameraCategory;
}
