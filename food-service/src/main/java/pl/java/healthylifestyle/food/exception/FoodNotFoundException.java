package pl.java.healthylifestyle.food.exception;

import lombok.Getter;

@Getter
public class FoodNotFoundException extends RuntimeException {

    private final String errorLabel;

    public FoodNotFoundException(String message, String errorLabel) {
        super(message);
        this.errorLabel = errorLabel;
    }
}
