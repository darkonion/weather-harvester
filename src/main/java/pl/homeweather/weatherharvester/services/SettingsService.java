package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.entity.Cron;
import pl.homeweather.weatherharvester.repositories.CronRepository;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class SettingsService {

    private final CronRepository cronRepository;

    public SettingsService(CronRepository cronRepository) {
        this.cronRepository = cronRepository;
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
}
