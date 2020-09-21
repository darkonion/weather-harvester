package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Flux;

@Repository
public interface BasicMeasurementRepository extends ReactiveCrudRepository<BasicMeasurement, Long> {

    @Query("SELECT * FROM basic_measurement " +
            "WHERE date > :#{#query.from} AND date < :#{#query.to} " +
            "AND id % :#{#query.interval} = 0 " +
            "ORDER BY id")
    Flux<BasicMeasurement> getMeasurementsInTimePeriodBetween(BasicQuery query);
}
