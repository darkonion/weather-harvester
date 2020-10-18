package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.homeweather.weatherharvester.entity.Cron;
import reactor.core.publisher.Mono;

@Repository
public interface CronRepository extends ReactiveCrudRepository<Cron, Long> {

    @Query("SELECT * FROM cron WHERE id = 1")
    Mono<Cron> getCurrentCron();

}
