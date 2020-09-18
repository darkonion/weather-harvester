package pw.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;


@Slf4j
@Service
public class BasicMeasurementService {

    @Transactional
    public void persistMeasurement(Mono<BasicMeasurement> basicMeasurement) {
        //TODO
        log.info(basicMeasurement.block().toString());
    }

}
