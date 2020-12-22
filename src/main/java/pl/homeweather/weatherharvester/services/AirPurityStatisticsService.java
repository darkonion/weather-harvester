package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.AirPurityDailyAverage;
import pl.homeweather.weatherharvester.entity.AirPurityMonthlyAverage;
import pl.homeweather.weatherharvester.repositories.AirPurityDailyAverageRepository;
import pl.homeweather.weatherharvester.repositories.AirPurityMonthlyAverageRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class AirPurityStatisticsService {

    private final AirPurityDailyAverageRepository dailyAverageRepository;
    private final AirPurityMonthlyAverageRepository monthlyAverageRepository;

    public AirPurityStatisticsService(AirPurityDailyAverageRepository dailyAverageRepository, AirPurityMonthlyAverageRepository monthlyAverageRepository) {
        this.dailyAverageRepository = dailyAverageRepository;
        this.monthlyAverageRepository = monthlyAverageRepository;
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
}
