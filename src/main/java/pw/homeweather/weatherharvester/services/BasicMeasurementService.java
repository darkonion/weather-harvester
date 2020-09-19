package pw.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;
import pw.homeweather.weatherharvester.repositories.BasicMeasurementRepository;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;


@Slf4j
@Service
public class BasicMeasurementService {

    private final BasicMeasurementRepository basicMeasurementRepository;

    public BasicMeasurementService(BasicMeasurementRepository basicMeasurementRepository) {
        this.basicMeasurementRepository = basicMeasurementRepository;
    }

    @Transactional
    public void persistMeasurement(Mono<BasicMeasurement> basicMeasurement) {
        basicMeasurementRepository.persistBasicMeasurement(
                basicMeasurement.filter(BasicMeasurement::isNotEmpty)
        );


    }
}
