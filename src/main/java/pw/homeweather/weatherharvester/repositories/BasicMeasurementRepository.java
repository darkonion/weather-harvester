package pw.homeweather.weatherharvester.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;

@Repository
public interface BasicMeasurementRepository extends ReactiveCrudRepository<BasicMeasurement, Long> {
}
