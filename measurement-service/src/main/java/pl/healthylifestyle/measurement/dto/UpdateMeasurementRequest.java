package pl.healthylifestyle.measurement.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateMeasurementRequest(
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
) {
}
