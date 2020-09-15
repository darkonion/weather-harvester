package pw.homeweather.weatherharvester.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.DirectProcessor;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Configuration
public class WebClientConfig {

    @Value("${station.url}")
    private String baseUrl;

    @Value("${station.token}")
    private String token;


    @Bean
    public WebClient webClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS));
                });

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .defaultHeader(HttpHeaders.AUTHORIZATION, format("Basic %s", token))
                .build();
    }
}
