package pw.homeweather.weatherharvester.entity;


import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
public class BasicMeasurement {

    @Id
    private Long id;

    private Double temperature;
    private Double pressure;
    private Double humidity;
    private Double lux;

    private LocalDateTime date;
}
