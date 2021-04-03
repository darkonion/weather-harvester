package pl.homeweather.weatherharvester.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "date")
public class Lightning {

    private Double energy;
    private Double distance;

    private LocalDateTime date = LocalDateTime.now();
}
