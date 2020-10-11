package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface BasicMeasurementRepository extends ReactiveCrudRepository<BasicMeasurement, Long> {

    @Query("SELECT * FROM basic_measurement " +
            "WHERE date > :#{#query.from} AND date < :#{#query.to} " +
            "AND id % :#{#query.interval} = 0 " +
            "ORDER BY id")
    Flux<BasicMeasurement> getMeasurementsInTimePeriodBetween(BasicQuery query);

    @Query("Select * FROM basic_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<BasicMeasurement> getLatestBasicMeasurement();

    @Query("Select temperature FROM basic_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<Double> getLatestTemperatureMeasurement();

    @Query("Select pressure FROM basic_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<Double> getLatestPressureMeasurement();

    @Query("Select humidity FROM basic_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<Double> getLatestHumidityMeasurement();

    @Query("SELECT count(id) FROM basic_measurement " +
            "WHERE date < :#{#time}")
    Mono<Integer> getNumberOnMeasurementsToClean(LocalDateTime time);

    @Query("DELETE FROM basic_measurement " +
            "WHERE date < :#{#time}")
    void cleanupMeasurementsOlderThan(LocalDateTime time);


}
