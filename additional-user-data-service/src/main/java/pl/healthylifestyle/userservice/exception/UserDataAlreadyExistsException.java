package pl.healthylifestyle.userservice.exception;

import lombok.Getter;

@Getter
public class UserDataAlreadyExistsException extends RuntimeException {

    private final String errorLabel;

    public UserDataAlreadyExistsException(String message, String errorLabel) {
        super(message);
        this.errorLabel = errorLabel;
    }
}
