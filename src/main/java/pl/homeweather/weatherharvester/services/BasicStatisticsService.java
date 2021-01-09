package pl.homeweather.weatherharvester.services;

import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.repositories.BasicMeasurementRepository;
import reactor.core.publisher.Mono;

@Service
public class BasicStatisticsService {

    private final BasicMeasurementRepository measurementRepository;

    public BasicStatisticsService(BasicMeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Mono<Integer> getNumberOfMeasurementsInDB() {
        return measurementRepository.getNumberOfMeasurementsInDB();
    }
}
