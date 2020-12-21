package pl.homeweather.weatherharvester.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirPurityDailyAverage {

    @Id
    private Long id;

    private Integer pm1;
    private Integer pm25;
    private Integer pm10;

    private LocalDate date;
}
