package pl.homeweather.weatherharvester.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.homeweather.weatherharvester.entity.AirPurityMeasurement;
import pl.homeweather.weatherharvester.services.AirPurityService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api")
public class AirPurityMeasurementController {

    private final AirPurityService airPurityService;

    public AirPurityMeasurementController(AirPurityService airPurityService) {
        this.airPurityService = airPurityService;
    }

    @GetMapping("/air")
    public Mono<List<AirPurityMeasurement>> getAirMeasurement(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(value = "int", required = false) Integer interval) {

        return airPurityService.getMeasurementsBetween(
                BasicQuery.getInstance(dateFrom, dateTo, interval));
    }
}
