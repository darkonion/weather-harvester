package pl.homeweather.weatherharvester.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "date")
public class AirPurityMeasurement {

    @Id
    private Long id;

    private Double pm1;
    private Double pm25;
    private Double pm10;

    private LocalDateTime date;

    private static final AirPurityMeasurement ap = new AirPurityMeasurement();

    @JsonIgnore
    public boolean isNotEmpty() {
        return !this.equals(ap);
    }
}
