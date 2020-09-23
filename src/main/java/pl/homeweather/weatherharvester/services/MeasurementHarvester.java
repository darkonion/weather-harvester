package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.homeweather.weatherharvester.entity.AirPurityMeasurement;
import pl.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.util.Arrays;

import static java.time.Duration.ofSeconds;


@Slf4j
@Service
public class MeasurementHarvester {

    private final WebClient webClient;
    private final BasicMeasurementService measurementService;
    private final AirPurityService airPurityService;

    private static final String BASIC_MEASUREMENT_URI = "/basic";
    private static final String AIR_MEASUREMENT_URI = "/air";

    public MeasurementHarvester(WebClient webClient, BasicMeasurementService measurementService, AirPurityService airPurityService) {
        this.webClient = webClient;
        this.measurementService = measurementService;
        this.airPurityService = airPurityService;
    }


    @Scheduled(cron = "${station.basic-cron}")
    public void harvestBasicMeasurement() {
        WebClient.RequestHeadersSpec<?> uri = webClient.get().uri(BASIC_MEASUREMENT_URI);
        Mono<BasicMeasurement> basicMeasurement;
        try {
            basicMeasurement = callForBasicMeasurement(uri);
        } catch (Exception e) {
            log.info("Cannot connect to Weather Station, error: {}", e.getMessage());
            log.debug(Arrays.toString(e.getStackTrace()));
            return;
        }
        measurementService.persistMeasurement(basicMeasurement).subscribe();
    }

    //@Scheduled(cron = "${station.air-cron}")
    public void harvestAirPurityMeasurement() {
        WebClient.RequestHeadersSpec<?> uri = webClient.get().uri(AIR_MEASUREMENT_URI);
        Mono<AirPurityMeasurement> airPurityMeasurement;
        try {
            airPurityMeasurement = callForAirMeasurement(uri);
        } catch (Exception e) {
            log.info("Cannot connect to Weather Station, error: {}", e.getMessage());
            log.debug(Arrays.toString(e.getStackTrace()));
            return;
        }
        airPurityService.persistMeasurement(airPurityMeasurement).subscribe();
    }

    private Mono<BasicMeasurement> callForBasicMeasurement(WebClient.RequestHeadersSpec<?> uri) {
        return uri.retrieve()
                .bodyToMono(BasicMeasurement.class)
                .retryWhen(getRetryBackoffSpec())
                .onErrorResume(e -> {
                    log.info("Timeout reached during connection!");
                    return Mono.just(new BasicMeasurement());
                });
    }

    private Mono<AirPurityMeasurement> callForAirMeasurement(WebClient.RequestHeadersSpec<?> uri) {
        return uri.retrieve()
                .bodyToMono(AirPurityMeasurement.class)
                .retryWhen(getRetryBackoffSpec())
                .onErrorResume(e -> {
                    log.info("Timeout reached during connection!");
                    return Mono.just(new AirPurityMeasurement());
                });
    }

    private RetryBackoffSpec getRetryBackoffSpec() {
        return Retry
                .fixedDelay(3, ofSeconds(5))
                .doBeforeRetry(r -> log.info(
                        "Retrying call, due to {}, attempt: {}", r.failure().getMessage(), r.totalRetries())
                );
    }
}
