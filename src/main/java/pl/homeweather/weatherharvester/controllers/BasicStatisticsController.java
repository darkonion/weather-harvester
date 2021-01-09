package pl.homeweather.weatherharvester.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.homeweather.weatherharvester.services.BasicStatisticsService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/statistics/basic")
public class BasicStatisticsController {

    private final BasicStatisticsService statisticsService;

    public BasicStatisticsController(BasicStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/count")
    public Mono<Integer> getNumberOfMeasurementsInDB() {
        return statisticsService.getNumberOfMeasurementsInDB();
    }
}
