package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enumuration.Event;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_reports")
public class EventReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Event category;
}
