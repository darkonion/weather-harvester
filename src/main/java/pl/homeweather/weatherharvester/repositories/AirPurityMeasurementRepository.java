package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.AirPurityMeasurement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface AirPurityMeasurementRepository extends ReactiveCrudRepository<AirPurityMeasurement, Long> {

    @Query("SELECT * FROM air_purity_measurement " +
            "WHERE date > :#{#query.from} AND date < :#{#query.to} " +
            "AND id % :#{#query.interval} = 0 " +
            "ORDER BY id")
    Flux<AirPurityMeasurement> getMeasurementsInTimePeriodBetween(BasicQuery query);

    @Query("SELECT * FROM air_purity_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<AirPurityMeasurement> getLatestMeasurement();

    @Query("SELECT count(id) FROM air_purity_measurement " +
            "WHERE date < :#{#time}")
    Mono<Integer> getNumberOnMeasurementsToClean(LocalDateTime time);

    @Query("DELETE FROM air_purity_measurement " +
            "WHERE date < :#{#time}")
    void cleanupMeasurementsOlderThan(LocalDateTime time);
}
