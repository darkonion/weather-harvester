package pl.homeweather.weatherharvester.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.homeweather.weatherharvester.entity.AirPurityMeasurement;
import pl.homeweather.weatherharvester.services.AirPurityService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AirPurityMeasurementController {

    private final AirPurityService airPurityService;

    public AirPurityMeasurementController(AirPurityService airPurityService) {
        this.airPurityService = airPurityService;
    }

    @GetMapping("/air-full")
    public Mono<List<AirPurityMeasurement>> getAirMeasurement(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(value = "int", required = false) Integer interval) {

        return airPurityService.getMeasurementsBetween(
                BasicQuery.getInstance(dateFrom, dateTo, interval));
    }

    @GetMapping("/air")
    public Mono<AirPurityMeasurement> getLatestMeasurement() {
        return airPurityService.getLatestMeasurement();
    }

    @PostMapping("/air")
    public void saveBasicMeasurement(@RequestBody Mono<AirPurityMeasurement> airMeasurement) {
        airPurityService
                .persistMeasurement(airMeasurement)
                .subscribe();
    }
}
