package pl.healthylifestyle.userservice.exception;

import lombok.Getter;

@Getter
public class UserDataNotFoundException extends RuntimeException {

    private final String errorLabel;

    public UserDataNotFoundException(String message, String errorLabel) {
        super(message);
        this.errorLabel = errorLabel;
    }
}
