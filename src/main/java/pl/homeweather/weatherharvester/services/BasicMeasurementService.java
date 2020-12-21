package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import pl.homeweather.weatherharvester.repositories.BasicMeasurementRepository;
import reactor.core.publisher.Mono;

import java.util.List;


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
                .map(m -> {
                    log.info("Persisted: " + m);
                    return m;
                });
    }

    public Mono<List<BasicMeasurement>> getMeasurementsBetween(BasicQuery basicQuery) {
        log.info("Basic Measurement Data request {}", basicQuery);

        return basicMeasurementRepository
                .getMeasurementsInTimePeriodBetween(basicQuery)
                .collectList()
                .flatMap(this::normalizeMeasurements);
    }

    private Mono<List<BasicMeasurement>> normalizeMeasurements(List<BasicMeasurement> measurements) {
        //TODO
        return Mono.just(measurements);
    }

    public Mono<BasicMeasurement> getLatestBasicMeasurement() {
        return basicMeasurementRepository.getLatestBasicMeasurement();
    }

    public Mono<Double> getLatestTemperatureMeasurement() {
        return basicMeasurementRepository.getLatestTemperatureMeasurement();
    }

    public Mono<Double> getLatestPressureMeasurement() {
        return basicMeasurementRepository.getLatestPressureMeasurement();
    }

    public Mono<Double> getLatestHumidityMeasurement() {
        return basicMeasurementRepository.getLatestHumidityMeasurement();
    }
}
