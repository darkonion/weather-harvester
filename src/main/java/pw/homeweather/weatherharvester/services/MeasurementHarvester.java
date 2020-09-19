package pw.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.util.Arrays;

import static java.lang.String.format;
import static java.time.Duration.ofSeconds;


@Slf4j
@Service
public class MeasurementHarvester {

    private final WebClient webClient;
    private final BasicMeasurementService measurementService;

    private static final String BASIC_MEASUREMENT_URI = "/basic";


    public MeasurementHarvester(WebClient webClient, BasicMeasurementService measurementService) {
        this.webClient = webClient;
        this.measurementService = measurementService;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void harvest() {
        WebClient.RequestHeadersSpec<?> uri = webClient.get().uri(BASIC_MEASUREMENT_URI);
        Mono<BasicMeasurement> basicMeasurement;
        try {
            basicMeasurement = callForMeasurement(uri);
        } catch (Exception e) {
            log.info("Cannot connect to Weather Station, error: " + e.getMessage());
            log.debug(Arrays.toString(e.getStackTrace()));
            return;
        }
        measurementService.persistMeasurement(basicMeasurement).subscribe();
    }

    private Mono<BasicMeasurement> callForMeasurement(WebClient.RequestHeadersSpec<?> uri) {
        return uri.retrieve()
                .bodyToMono(BasicMeasurement.class)
                .retryWhen(getRetryBackoffSpec())
                .onErrorResume(e -> {
                    log.info("Timeout reached during connection!");
                    return Mono.just(new BasicMeasurement());});
    }

    private RetryBackoffSpec getRetryBackoffSpec() {
        return Retry
                .fixedDelay(3, ofSeconds(5))
                .doBeforeRetry(r -> log.info(
                        format("Retrying call, due to %s, attempt: %d", r.failure().getMessage(), r.totalRetries())
                ));
    }
}
