package pw.homeweather.weatherharvester.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class BasicMeasurement {

    @Id
    private Long id;

    private double temperature;
}
