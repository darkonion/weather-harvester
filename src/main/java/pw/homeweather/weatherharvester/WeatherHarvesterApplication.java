package pw.homeweather.weatherharvester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableR2dbcRepositories
@SpringBootApplication
public class WeatherHarvesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherHarvesterApplication.class, args);
	}

}
