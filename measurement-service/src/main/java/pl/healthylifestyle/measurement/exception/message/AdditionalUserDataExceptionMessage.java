package pl.healthylifestyle.measurement.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AdditionalUserDataExceptionMessage {

    MEASUREMENT_NOT_FOUND_ERROR("Measurement %s not found"),
    MEASUREMENT_ALREADY_EXISTS_ERROR("Measurement %s not found");

    private final String message;
}
