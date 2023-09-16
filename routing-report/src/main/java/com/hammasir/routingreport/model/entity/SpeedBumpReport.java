package com.hammasir.routingreport.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@Entity
@Table(name = "speed_bump_reports")
public class SpeedBumpReport extends Report {

}
