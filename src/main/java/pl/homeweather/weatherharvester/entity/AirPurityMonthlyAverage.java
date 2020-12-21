package pl.homeweather.weatherharvester.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.YearMonth;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AirPurityMonthlyAverage {

    @Id
    private Long id;

    private Integer pm1;
    private Integer pm25;
    private Integer pm10;

    private YearMonth date;
}
