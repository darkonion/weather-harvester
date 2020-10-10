package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.homeweather.weatherharvester.repositories.AirPurityMeasurementRepository;
import pl.homeweather.weatherharvester.repositories.BasicMeasurementRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CleaningService {

    @Value("${harvester.cleanup.months}")
    private Integer months;

    private final AirPurityMeasurementRepository airPurityMeasurementRepository;
    private final BasicMeasurementRepository basicMeasurementRepository;

    public CleaningService(AirPurityMeasurementRepository airPurityMeasurementRepository, BasicMeasurementRepository basicMeasurementRepository) {
        this.airPurityMeasurementRepository = airPurityMeasurementRepository;
        this.basicMeasurementRepository = basicMeasurementRepository;
    }

    @Transactional
//    @Scheduled(cron = "0 */5 * * * *")
    public void doCleanup() {
        LocalDateTime cleanupCutoffDate = LocalDateTime.now()
                .minusMonths(months);

        log.info("Performing Cleaning. Number of basic measurements to clean: {}, air measurements to clean: {}",
                basicMeasurementRepository
                        .getNumberOnMeasurementsToClean(cleanupCutoffDate),
                airPurityMeasurementRepository
                        .getNumberOnMeasurementsToClean(cleanupCutoffDate));

        try {
            cleaning(cleanupCutoffDate);
        } catch (Exception e) {
            log.error("Error during cleaning database! " + e.getMessage());
        }
    }

    private void cleaning(LocalDateTime cleanupCutoffDate) {
        airPurityMeasurementRepository.cleanupMeasurementsOlderThan(cleanupCutoffDate);
        basicMeasurementRepository.cleanupMeasurementsOlderThan(cleanupCutoffDate);
    }
}
