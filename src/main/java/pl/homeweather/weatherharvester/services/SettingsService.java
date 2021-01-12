package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.entity.Cron;
import pl.homeweather.weatherharvester.entity.SensorSettings;
import pl.homeweather.weatherharvester.repositories.CronRepository;
import pl.homeweather.weatherharvester.repositories.SensorSettingsRepository;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class SettingsService {

    private final CronRepository cronRepository;
    private final SensorSettingsRepository settingsRepository;

    public SettingsService(CronRepository cronRepository, SensorSettingsRepository settingsRepository) {
        this.cronRepository = cronRepository;
        this.settingsRepository = settingsRepository;
    }

    public Mono<Cron> getCurrentCron() {
        return cronRepository.getCurrentCron();
    }

    public Mono<Cron> updateCron(Mono<Cron> cron) {
        return cron
                .filter(Objects::nonNull)
                .flatMap(cronRepository::save)
                .flatMap(c -> {
                    log.info("Updated Cron: " + c);
                    return Mono.fromSupplier(() -> c);
                });
    }

    public Mono<SensorSettings> getCurrentSensorSettings() {
        return settingsRepository.findById(1L);
    }

    public Mono<SensorSettings> updateTemperatureSensor(Mono<String> sensor) {
        return sensor
                .filter(Objects::nonNull)
                .filter(s -> s.equals("W1") || s.equals("BME280"))
                .flatMap(settingsRepository::updateTemperatureSensor)
                .flatMap(s -> {
                    log.info("Temperature sensor changed to: " + s.getTemperature());
                    return Mono.fromSupplier(() -> s);
                });
    }
}
