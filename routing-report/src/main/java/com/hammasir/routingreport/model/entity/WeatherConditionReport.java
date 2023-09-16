package com.hammasir.routingreport.model.entity;

import com.hammasir.routingreport.model.enums.WeatherCondition;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weather_condition_reports")
public class WeatherConditionReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private WeatherCondition weatherConditionCategory;
}
