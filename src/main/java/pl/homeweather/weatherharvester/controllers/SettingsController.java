package pl.homeweather.weatherharvester.controllers;

import org.springframework.web.bind.annotation.*;
import pl.homeweather.weatherharvester.entity.Cron;
import pl.homeweather.weatherharvester.entity.SensorSettings;
import pl.homeweather.weatherharvester.services.SettingsService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/cron")
    public Mono<Cron> getCron() {
        return settingsService.getCurrentCron();
    }

    @PutMapping("/cron")
    public Mono<Void> updateCron(@RequestBody Mono<Cron> cron) {
        return settingsService
                .updateCron(cron)
                .then();
    }

    @GetMapping("/sensors")
    public Mono<SensorSettings> getSensorSettings() {
        return settingsService.getCurrentSensorSettings();
    }

    @PutMapping("/sensors/temp")
    public Mono<Void> updateTemperatureSensor(@RequestParam("s") String sensor) {
        return settingsService
                .updateTemperatureSensor(Mono.just(sensor))
                .then();
    }
}
