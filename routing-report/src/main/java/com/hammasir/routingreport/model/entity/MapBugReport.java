package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.MapBug;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "map_bug_reports")
public class MapBugReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private MapBug mapBugCategory;
}
