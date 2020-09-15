package pw.homeweather.weatherharvester.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;

@RestController
public class MainController {

    private final WebClient webClient;

    private static final String BASIC_MEASUREMENT_URI = "/temp";

    public MainController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/test")
    public BasicMeasurement test() {
        WebClient.RequestHeadersSpec<?> uri = webClient.get().uri(BASIC_MEASUREMENT_URI);
        return uri.retrieve().bodyToMono(BasicMeasurement.class).block();
    }
}
