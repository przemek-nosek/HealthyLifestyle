package pl.healthylifestyle.measurement.exception;

import lombok.Getter;

@Getter
public class MeasurementAlreadyExistsException extends RuntimeException {

    private final String errorLabel;

    public MeasurementAlreadyExistsException(String message, String errorLabel) {
        super(message);
        this.errorLabel = errorLabel;
    }
}
