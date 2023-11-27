package pl.java.healthylifestyle.food.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Allergen {
    LACTOSE("lactose"),
    GLUTEN("gluten"),
    CRUSTACEANS("crustaceans"),
    EGGS("eggs"),
    FISH("fish"),
    NUTS("nuts"),
    SOY("soy"),
    MILK("milk"),
    CELERY("celery"),
    SESAME("sesame");

    private final String value;
}
