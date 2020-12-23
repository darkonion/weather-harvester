package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.AirPurityDailyAverage;
import pl.homeweather.weatherharvester.entity.AirPurityMeasurement;
import pl.homeweather.weatherharvester.entity.AirPurityMonthlyAverage;
import pl.homeweather.weatherharvester.repositories.AirPurityDailyAverageRepository;
import pl.homeweather.weatherharvester.repositories.AirPurityMeasurementRepository;
import pl.homeweather.weatherharvester.repositories.AirPurityMonthlyAverageRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
public class AirPurityStatisticsTasks {

    private final AirPurityDailyAverageRepository dailyAverageRepository;
    private final AirPurityMonthlyAverageRepository monthlyAverageRepository;
    private final AirPurityMeasurementRepository measurementRepository;

    public AirPurityStatisticsTasks(AirPurityDailyAverageRepository dailyAverageRepository,
                                    AirPurityMonthlyAverageRepository monthlyAverageRepository,
                                    AirPurityMeasurementRepository measurementRepository) {
        this.dailyAverageRepository = dailyAverageRepository;
        this.monthlyAverageRepository = monthlyAverageRepository;
        this.measurementRepository = measurementRepository;
    }

    @Scheduled(cron = "0 1 0 * * *")
    private void performDailyAverage() {
        persistDailyAverage(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusMinutes(4)
        ).subscribe();
    }

    @Scheduled(cron = "0 2 0 1 * *")
    private void performMonthlyAverage() {
        persistMonthlyAverage(
                LocalDateTime.now().minusMonths(1),
                LocalDateTime.now().minusMinutes(5)
        ).subscribe();
    }

    private Mono<AirPurityDailyAverage> persistDailyAverage(LocalDateTime from, LocalDateTime to) {
        return captureDailyAverage(from, to)
                .filter(AirPurityDailyAverage::isNotEmpty)
                .flatMap(dailyAverageRepository::save)
                .map(saved -> {
                    log.info("Persisted: {}", saved);
                    return saved;
                });
    }

    private Mono<AirPurityMonthlyAverage> persistMonthlyAverage(LocalDateTime from, LocalDateTime to) {
        return captureMonthlyAverage(from, to)
                .filter(AirPurityMonthlyAverage::isNotEmpty)
                .flatMap(monthlyAverageRepository::save)
                .map(saved -> {
                    log.info("Persisted: {}", saved);
                    return saved;
                });
    }

    private Mono<AirPurityDailyAverage> captureDailyAverage(LocalDateTime from, LocalDateTime to) {
        return measurementRepository
                .getMeasurementsInTimePeriodBetween(BasicQuery.getInstance(from, to, 1))
                .collectList()
                .map((List<AirPurityMeasurement> list) -> {
                    AirPurityDailyAverage dailyAverage = getAverage(list, new AirPurityDailyAverage());
                    dailyAverage.setDate(from);
                    return dailyAverage;
                });
    }

    private Mono<AirPurityMonthlyAverage> captureMonthlyAverage(LocalDateTime from, LocalDateTime to) {
        return dailyAverageRepository.getMeasurementsInTimePeriodBetween(BasicQuery.getInstance(from, to, 1))
                .collectList()
                .map((List<AirPurityDailyAverage> list) -> {
                    AirPurityMonthlyAverage monthlyAverage = getAverage(list, new AirPurityMonthlyAverage());
                    monthlyAverage.setDate(from);
                    return monthlyAverage;
                });
    }

    private <T extends AirPurityMeasurement, N extends AirPurityMeasurement> T getAverage(List<N> measurements, T average) {

        int pm1average = 0;
        int pm25average = 0;
        int pm10average = 0;
        int size = measurements.size();

        if (size == 0) {
            return average;
        }

        for (N measurement : measurements) {
            pm1average += ofNullable(measurement.getPm1()).orElse(0);
            pm10average += ofNullable(measurement.getPm10()).orElse(0);
            pm25average += ofNullable(measurement.getPm25()).orElse(0);
        }

        average.setPm1(pm1average / size);
        average.setPm25(pm25average / size);
        average.setPm10(pm10average / size);

        return average;
    }
}
