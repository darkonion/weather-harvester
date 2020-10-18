package pl.homeweather.weatherharvester.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Cron {

    @Id
    private Long id;
    private String airCron;
    private String basicCron;
}
