package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface BasicMeasurementRepository extends ReactiveCrudRepository<BasicMeasurement, Long> {

    @Query("SELECT * FROM basic_measurement WHERE date > :from AND date < :to ORDER BY id")
    Flux<BasicMeasurement> getMeasurementsInTimePeriodBetween(LocalDateTime from, LocalDateTime to);
}
