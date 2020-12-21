package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.controllers.BasicQuery;
import pl.homeweather.weatherharvester.entity.AirPurityDailyAverage;
import pl.homeweather.weatherharvester.entity.AirPurityMeasurement;
import pl.homeweather.weatherharvester.repositories.AirPurityDailyAverageRepository;
import pl.homeweather.weatherharvester.repositories.AirPurityMeasurementRepository;
import pl.homeweather.weatherharvester.repositories.AirPurityMonthlyAverageRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Optional.ofNullable;

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

    @Scheduled(cron = "0 1 0 * * *")
    private void performDailyAverage() {
        persistDailyAverage(
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().minusMinutes(3)
        ).subscribe();
    }

    private Mono<AirPurityDailyAverage> persistDailyAverage(LocalDateTime from, LocalDateTime to) {
        return captureDailyAverage(from, to)
                .flatMap(dailyAverageRepository::save)
                .map(saved -> {
                    log.info("Persisted: " + saved);
                    return saved;
                });
    }

    private Mono<AirPurityDailyAverage> captureDailyAverage(LocalDateTime from, LocalDateTime to) {
        return measurementRepository
                .getMeasurementsInTimePeriodBetween(BasicQuery.getInstance(from, to, 1)).collectList()
                .map((List<AirPurityMeasurement> list) -> {
                    AirPurityDailyAverage dailyAverage = getDailyAverage(list);
                    dailyAverage.setDate(from.toLocalDate());
                    return dailyAverage;
                });
    }

    private AirPurityDailyAverage getDailyAverage(List<AirPurityMeasurement> measurements) {

        int pm1average = 0;
        int pm25average = 0;
        int pm10average = 0;
        int size = measurements.size();

        for (AirPurityMeasurement measurement : measurements) {
            pm1average += ofNullable(measurement.getPm1()).orElse(0);
            pm10average += ofNullable(measurement.getPm10()).orElse(0);
            pm25average += ofNullable(measurement.getPm25()).orElse(0);
        }

        return AirPurityDailyAverage.builder()
                .pm1(pm1average/size)
                .pm25(pm25average/size)
                .pm10(pm10average/size)
                .build();
    }

    //@PostConstruct
    public void captureEarlierData() {
        LocalDateTime from = LocalDateTime.of(2020, 10, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2020, 10, 1, 23, 59);

        while (to.isBefore(LocalDateTime.now())) {
            persistDailyAverage(from, to).subscribe();
            from = from.plusDays(1);
            to = to.plusDays(1);
        }
    }
}
