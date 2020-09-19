package pw.homeweather.weatherharvester.repositories;


import pw.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Mono;

public interface BasicMeasurementRepository {

    void persistBasicMeasurement(Mono<BasicMeasurement> basicMeasurement);
}
