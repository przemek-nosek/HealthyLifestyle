package pl.healthylifestyle.measurement.dto;

import java.time.LocalDate;

public record MeasurementDto (
        String uuid,
        double weight,
        double calfSize,
        double thighSize,
        double waistSize,
        double chestSize,
        double neckSize,
        double forearmSize,
        double bicepsSize,
        LocalDate measurementDate,
        String userUuid
){
}
