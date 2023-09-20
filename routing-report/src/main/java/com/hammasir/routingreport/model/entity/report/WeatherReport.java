package com.hammasir.routingreport.model.entity.report;

import com.hammasir.routingreport.model.entity.Report;
import com.hammasir.routingreport.model.enumuration.Weather;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weather_reports")
public class WeatherReport extends Report {

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Weather category;
}
