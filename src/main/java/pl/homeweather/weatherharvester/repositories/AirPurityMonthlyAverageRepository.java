package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pl.homeweather.weatherharvester.entity.AirPurityMonthlyAverage;

@Repository
public interface AirPurityMonthlyAverageRepository extends ReactiveCrudRepository<AirPurityMonthlyAverage, Long> {
}
