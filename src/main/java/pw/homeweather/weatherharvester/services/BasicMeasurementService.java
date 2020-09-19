package pw.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;
import pw.homeweather.weatherharvester.repositories.BasicMeasurementRepository;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class BasicMeasurementService {

    private final BasicMeasurementRepository basicMeasurementRepository;

    public BasicMeasurementService(BasicMeasurementRepository basicMeasurementRepository) {
        this.basicMeasurementRepository = basicMeasurementRepository;
    }

    @Transactional
    public Mono<BasicMeasurement> persistMeasurement(Mono<BasicMeasurement> basicMeasurement) {
        return basicMeasurement
                .filter(BasicMeasurement::isNotEmpty)
                .flatMap(basicMeasurementRepository::save)
                .flatMap(m -> {
                    log.info("Persisted: " + m);
                    return Mono.fromSupplier(() -> m);
                });
    }
}
