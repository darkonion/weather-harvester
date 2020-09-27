package pl.homeweather.weatherharvester.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import pl.homeweather.weatherharvester.services.BasicMeasurementService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class BasicMeasurementController {

    private final BasicMeasurementService basicService;

    public BasicMeasurementController(BasicMeasurementService basicService) {
        this.basicService = basicService;
    }

    @GetMapping("/basic-full")
    public Mono<List<BasicMeasurement>> getBasicMeasurement(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(value = "int", required = false) Integer interval) {

        return basicService.getMeasurementsBetween(
                BasicQuery.getInstance(dateFrom, dateTo, interval));
    }

    @GetMapping("/basic")
    public Mono<BasicMeasurement> getLatestBasicMeasurement() {
        return basicService.getLatestBasicMeasurement();
    }

    @GetMapping("/temperature")
    public Mono<Double> getLatestTemperatureMeasurement() {
        return basicService.getLatestTemperatureMeasurement();
    }

    @GetMapping("/pressure")
    public Mono<Double> getLatestPressureMeasurement() {
        return basicService.getLatestPressureMeasurement();
    }

    @GetMapping("/humidity")
    public Mono<Double> getLatestHumidityMeasurement() {
        return basicService.getLatestHumidityMeasurement();
    }
}
