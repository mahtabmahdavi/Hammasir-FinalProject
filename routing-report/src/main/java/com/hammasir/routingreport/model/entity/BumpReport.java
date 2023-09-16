package com.hammasir.routingreport.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@Entity
@Table(name = "bump_reports")
public class BumpReport extends Report {

}
