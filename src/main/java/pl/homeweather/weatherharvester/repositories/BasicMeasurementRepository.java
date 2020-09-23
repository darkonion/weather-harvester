package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BasicMeasurementRepository extends ReactiveCrudRepository<BasicMeasurement, Long> {

    @Query("SELECT * FROM basic_measurement " +
            "WHERE date > :#{#query.from} AND date < :#{#query.to} " +
            "AND id % :#{#query.interval} = 0 " +
            "ORDER BY id")
    Flux<BasicMeasurement> getMeasurementsInTimePeriodBetween(BasicQuery query);

    @Query("Select temperature FROM basic_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<Double> getLatestTemperatureMeasurement();

    @Query("Select pressure FROM basic_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<Double> getLatestPressureMeasurement();

    @Query("Select humidityFROM basic_measurement " +
            "ORDER BY id DESC LIMIT 1")
    Mono<Double> getLatestHumidityMeasurement();


}
