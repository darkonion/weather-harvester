package pw.homeweather.weatherharvester.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.retry.Repeat;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class MeasurementHarvester {

    private final WebClient webClient;

    private static final String BASIC_MEASUREMENT_URI = "/tempp";


    public MeasurementHarvester(WebClient webClient) {
        this.webClient = webClient;
    }

    @Scheduled(fixedRate = 4000)
    public void harvest() {
        WebClient.RequestHeadersSpec<?> uri = webClient.get().uri(BASIC_MEASUREMENT_URI);

        //TODO
        System.out.println(
                uri
                        .exchange()
                        .retryWhen(Retry.fixedDelay(10 , Duration.ofSeconds(10)))
                        .filter(clientResponse -> clientResponse.rawStatusCode() == 200)
                        .repeatWhenEmpty(Repeat.onlyIf(r -> true)
                                .fixedBackoff(Duration.ofSeconds(5))
                                .timeout(Duration.ofSeconds(10)))
                        .block()
                        .rawStatusCode()
        );

    }
}
