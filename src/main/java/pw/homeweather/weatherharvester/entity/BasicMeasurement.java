package pw.homeweather.weatherharvester.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
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

    public boolean isNotEmpty() {
        return !this.equals(bm);
    }
}
