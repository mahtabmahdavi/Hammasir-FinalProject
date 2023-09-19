package com.hammasir.routingreport.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "report_type", nullable = false)
    private String type;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "like_counter")
    private int likeCounter;

    @Transient
    private Integer duration;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;

    @Column(name = "location", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Geometry location;

    @ElementCollection
    @CollectionTable(name = "report_contributors", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "contributor")
    private List<Long> contributors;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
