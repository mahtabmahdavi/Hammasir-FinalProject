package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.Place;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "place_reports")
public class PlaceReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Place category;
}
