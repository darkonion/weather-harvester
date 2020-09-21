package pl.homeweather.weatherharvester.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import pl.homeweather.weatherharvester.services.BasicMeasurementService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class MainController {

    private final BasicMeasurementService basicService;

    public MainController(BasicMeasurementService basicService) {
        this.basicService = basicService;
    }

    @GetMapping("/basic")
    public Mono<List<BasicMeasurement>> getBasicMeasurement(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(value = "int", required = false) Integer interval) {

        return basicService.getMeasurementsBetween(
                BasicQuery.getInstance(dateFrom, dateTo, interval));
    }
}
