package pl.homeweather.weatherharvester.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private static final Lightning emptyLightning = new Lightning();

    @JsonIgnore
    public boolean isNotEmpty() {
        return !this.equals(emptyLightning);
    }
}
