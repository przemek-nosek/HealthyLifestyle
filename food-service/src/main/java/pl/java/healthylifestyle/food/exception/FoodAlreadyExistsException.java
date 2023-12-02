package pl.java.healthylifestyle.food.exception;

import lombok.Getter;

@Getter
public class FoodAlreadyExistsException extends RuntimeException {

    private final String errorLabel;

    public FoodAlreadyExistsException(String message, String errorLabel) {
        super(message);
        this.errorLabel = errorLabel;
    }
}
