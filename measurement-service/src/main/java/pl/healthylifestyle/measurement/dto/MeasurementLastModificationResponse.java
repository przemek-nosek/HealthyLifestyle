package pl.healthylifestyle.measurement.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MeasurementLastModificationResponse(LocalDate lastModification) {

    public static MeasurementLastModificationResponse empty() {
        return MeasurementLastModificationResponse.builder()
                .lastModification(null)
                .build();
    }
}
