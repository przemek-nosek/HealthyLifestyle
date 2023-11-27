package pl.java.healthylifestyle.food.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Shop {
    BIEDRONKA("Biedronka"),
    AUCHAN("Auchan"),
    ALDI("Aldi"),
    NETTO("Netto"),
    DINO("Dino"),
    SPOLEM("Społem"),
    KAUFLAND("Kaufland"),
    STROKROTKA("Strokrotka"),
    CARREFOUR("Carrefour"),
    ZABKA("Żabka"),
    LEWIATAN("Lewiatan"),
    LIDL("Lidl");

    private final String value;
}
