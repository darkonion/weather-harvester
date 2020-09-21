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
public class BasicMeasurement {

    @Id
    private Long id;

    private Double temperature;
    private Double pressure;
    private Double humidity;
    private Double lux;

    private LocalDateTime date;

    private static final BasicMeasurement bm = new BasicMeasurement();

    @JsonIgnore
    public boolean isNotEmpty() {
        return !this.equals(bm);
    }
}
