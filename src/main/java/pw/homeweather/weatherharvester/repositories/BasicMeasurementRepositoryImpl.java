package pw.homeweather.weatherharvester.repositories;

import org.springframework.stereotype.Repository;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Mono;

@Repository
public class BasicMeasurementRepositoryImpl implements BasicMeasurementRepository {

    @Override
    public void persistBasicMeasurement(Mono<BasicMeasurement> basicMeasurementMono) {

    }
}
