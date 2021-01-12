package pl.homeweather.weatherharvester.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.homeweather.weatherharvester.entity.SensorSettings;
import reactor.core.publisher.Mono;

public interface SensorSettingsRepository extends ReactiveCrudRepository<SensorSettings, Long> {

    @Query("UPDATE sensor_settings SET temperature = :#{#sensor} WHERE id = 1 RETURNING *")
    Mono<SensorSettings> updateTemperatureSensor(String sensor);
}
