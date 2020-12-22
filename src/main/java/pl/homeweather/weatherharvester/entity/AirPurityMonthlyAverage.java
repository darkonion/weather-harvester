package pl.homeweather.weatherharvester.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AirPurityMonthlyAverage extends AirPurityMeasurement {

    @Override
    public String toString() {
        return String.format("%s(id=%d, date=%s, pm1=%d, pm25=%d, pm10=%d)",
                this.getClass().getSimpleName(),
                super.getId(),
                super.getDate(),
                super.getPm1(),
                super.getPm25(),
                super.getPm10());
    }
}
