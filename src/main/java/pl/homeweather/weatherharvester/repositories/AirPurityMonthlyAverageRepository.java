package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.AirPurityMonthlyAverage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AirPurityMonthlyAverageRepository extends ReactiveCrudRepository<AirPurityMonthlyAverage, Long> {

    @Query("SELECT * FROM air_purity_monthly_average " +
            "WHERE date > :#{#query.from} AND date < :#{#query.to} " +
            "AND id % :#{#query.interval} = 0 " +
            "ORDER BY id")
    Flux<AirPurityMonthlyAverage> getMeasurementsInTimePeriodBetween(BasicQuery query);

    @Query("SELECT * FROM air_purity_monthly_average ORDER BY id DESC LIMIT 1")
    Mono<AirPurityMonthlyAverage> getLatestMeasurement();
}
