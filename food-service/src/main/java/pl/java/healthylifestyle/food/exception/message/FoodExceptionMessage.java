package pl.java.healthylifestyle.food.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FoodExceptionMessage {

    FOOD_NOT_FOUND_ERROR("Food %s not found"),
    FOOD_ALREADY_EXISTS_ERROR("Food %s already exists");

    private final String message;
}
