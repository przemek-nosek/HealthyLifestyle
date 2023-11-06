package pl.healthylifestyle.measurement.exception;

import lombok.Getter;

@Getter
public class MeasurementNotFoundException extends RuntimeException {

    private final String errorLabel;

    public MeasurementNotFoundException(String message, String errorLabel) {
        super(message);
        this.errorLabel = errorLabel;
    }
}
