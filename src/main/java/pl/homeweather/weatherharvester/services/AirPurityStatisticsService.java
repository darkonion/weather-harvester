package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.AirPurityDailyAverage;
import pl.homeweather.weatherharvester.entity.AirPurityMonthlyAverage;
import pl.homeweather.weatherharvester.repositories.AirPurityDailyAverageRepository;
import pl.homeweather.weatherharvester.repositories.AirPurityMeasurementRepository;
import pl.homeweather.weatherharvester.repositories.AirPurityMonthlyAverageRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class AirPurityStatisticsService {

    private final AirPurityDailyAverageRepository dailyAverageRepository;
    private final AirPurityMonthlyAverageRepository monthlyAverageRepository;
    private final AirPurityMeasurementRepository measurementRepository;

    public AirPurityStatisticsService(AirPurityDailyAverageRepository dailyAverageRepository, AirPurityMonthlyAverageRepository monthlyAverageRepository, AirPurityMeasurementRepository measurementRepository) {
        this.dailyAverageRepository = dailyAverageRepository;
        this.monthlyAverageRepository = monthlyAverageRepository;
        this.measurementRepository = measurementRepository;
    }

    public Mono<List<AirPurityDailyAverage>> getDailyAverageBetween(BasicQuery basicQuery) {
        log.info("Air daily average request {}", basicQuery);

        return dailyAverageRepository
                .getMeasurementsInTimePeriodBetween(basicQuery)
                .collectList();
    }

    public Mono<AirPurityDailyAverage> getLatestDailyAverage() {
        return dailyAverageRepository.getLatestMeasurement();
    }

    public Mono<List<AirPurityMonthlyAverage>> getMonthlyAverageBetween(BasicQuery basicQuery) {
        log.info("Air monthly average request {}", basicQuery);

        return monthlyAverageRepository
                .getMeasurementsInTimePeriodBetween(basicQuery)
                .collectList();
    }

    public Mono<AirPurityMonthlyAverage> getLatestMonthlyAverage() {
        return monthlyAverageRepository.getLatestMeasurement();
    }

    public Mono<Integer> getNumberOfMeasurementsInDB() {
        return measurementRepository.getNumberOfMeasurementsInDB();
    }
}
