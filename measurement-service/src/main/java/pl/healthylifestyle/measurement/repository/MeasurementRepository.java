package pl.healthylifestyle.measurement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.healthylifestyle.measurement.entity.Measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    Optional<Measurement> findByUuid(String uuid);

    List<Measurement> findAllByUserUuid(String userUuid);

    Optional<Measurement> findTopByUserUuidOrderByMeasurementDateDesc(String userUuid);
}
