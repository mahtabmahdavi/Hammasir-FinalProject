package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.EventWay;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_way_reports")
public class EventWayReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private EventWay eventWayCategory;
}
