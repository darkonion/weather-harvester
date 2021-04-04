package pl.homeweather.weatherharvester.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.homeweather.weatherharvester.entity.Lightning;
import pl.homeweather.weatherharvester.services.RecentLightningService;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/lightning")
public class LightningController {

    private final RecentLightningService recentLightningService;

    public LightningController(RecentLightningService recentLightningService) {
        this.recentLightningService = recentLightningService;
    }

    @PostMapping
    public Mono<Void> catchLightning(@RequestBody Mono<Lightning> lightning) {
        return recentLightningService.addToQueue(lightning).then();
    }

    @GetMapping
    public Mono<List<Lightning>> getLightningList() {
        return recentLightningService.getLightnings();
    }
}
