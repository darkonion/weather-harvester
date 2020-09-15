package pw.homeweather.weatherharvester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
public class WeatherHarvesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherHarvesterApplication.class, args);
	}

}
