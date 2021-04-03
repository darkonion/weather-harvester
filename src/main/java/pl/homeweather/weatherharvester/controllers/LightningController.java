package pl.homeweather.weatherharvester.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.homeweather.weatherharvester.entity.Lightning;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/lightning")
public class LightningController {

    @PostMapping
    public Mono<Void> catchLightning(@RequestBody Mono<Lightning> lightning) {
        return lightning.doOnNext(l -> log.info("Lightning catched: " + l)).then();
    }
}
