package pl.homeweather.weatherharvester.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.homeweather.weatherharvester.entity.AirPurityDailyAverage;
import pl.homeweather.weatherharvester.entity.AirPurityMonthlyAverage;
import pl.homeweather.weatherharvester.services.AirPurityStatisticsService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/statistics/air")
public class AirPurityStatisticsController {

    private final AirPurityStatisticsService statisticsService;

    public AirPurityStatisticsController(AirPurityStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/daily")
    public Mono<List<AirPurityDailyAverage>> getAirPurityDailyAverage(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(value = "int", required = false) Integer interval) {

        return statisticsService.getDailyAverageBetween(
                BasicQuery.getInstance(dateFrom, dateTo, interval));
    }

    @GetMapping("/daily-latest")
    public Mono<AirPurityDailyAverage> getLatestAirPurityDailyAverage() {
        return statisticsService.getLatestDailyAverage();
    }

    @GetMapping("/monthly")
    public Mono<List<AirPurityMonthlyAverage>> getAirPurityMonthlyAverage(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(value = "int", required = false) Integer interval) {

        return statisticsService.getMonthlyAverageBetween(
                BasicQuery.getInstance(dateFrom, dateTo, interval));
    }

    @GetMapping("/monthly-latest")
    public Mono<AirPurityMonthlyAverage> getLatestAirPurityMonthlyAverage() {
        return statisticsService.getLatestMonthlyAverage();
    }
}
