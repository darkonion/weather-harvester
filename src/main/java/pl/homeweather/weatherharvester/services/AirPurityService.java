package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.AirPurityMeasurement;
import pl.homeweather.weatherharvester.repositories.AirPurityMeasurementRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class AirPurityService {

    private final AirPurityMeasurementRepository airPurityMeasurementRepository;

    public AirPurityService(AirPurityMeasurementRepository airPurityMeasurementRepository) {
        this.airPurityMeasurementRepository = airPurityMeasurementRepository;
    }

    @Transactional
    public Mono<AirPurityMeasurement> persistMeasurement(Mono<AirPurityMeasurement> airMeasurement) {
        return airMeasurement
                .filter(AirPurityMeasurement::isNotEmpty)
                .flatMap(airPurityMeasurementRepository::save)
                .flatMap(m -> {
                    log.info("Persisted: " + m);
                    return Mono.fromSupplier(() -> m);
                });
    }

    public Mono<List<AirPurityMeasurement>> getMeasurementsBetween(BasicQuery basicQuery) {
        log.info("Basic Measurement Data request {}", basicQuery);

        return airPurityMeasurementRepository
                .getMeasurementsInTimePeriodBetween(basicQuery)
                .collectList()
                .flatMap(this::normalizeMeasurements);
    }

    public Mono<AirPurityMeasurement> getLatestMeasurement() {
        return airPurityMeasurementRepository.getLatestMeasurement();
    }

    private Mono<List<AirPurityMeasurement>> normalizeMeasurements(List<AirPurityMeasurement> measurements) {
        //TODO
        return Mono.just(measurements);
    }
}
