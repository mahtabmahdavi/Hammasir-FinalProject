package com.hammasir.routingreport.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id")
    @SequenceGenerator(name = "report_id", sequenceName = "report_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;

    @Column(name = "location", columnDefinition = "geometry(Point, 4326)")
    private Geometry location;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
