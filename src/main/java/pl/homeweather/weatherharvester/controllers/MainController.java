package pl.homeweather.weatherharvester.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import pl.homeweather.weatherharvester.services.BasicMeasurementService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    private final BasicMeasurementService basicService;

    public MainController(BasicMeasurementService basicService) {
        this.basicService = basicService;
    }

    @GetMapping("/basicm")
    public Mono<List<BasicMeasurement>> getBasicMeasurement(
            @RequestAttribute(name = "from", required = false) Optional<LocalDateTime> dateFrom,
            @RequestAttribute(name = "to", required = false) Optional<LocalDateTime> dateTo) {

        return basicService.getMeasurementsBetween(
                dateFrom.orElse(LocalDateTime.now().minusDays(1)),
                dateTo.orElse(LocalDateTime.now())
        );
    }
}
