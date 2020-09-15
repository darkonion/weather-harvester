package pw.homeweather.weatherharvester.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pw.homeweather.weatherharvester.entity.BasicMeasurement;

@Repository
public interface BasicMeasurementRepository extends JpaRepository<BasicMeasurement, Long> {
}
