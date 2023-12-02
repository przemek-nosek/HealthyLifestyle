package pl.java.healthylifestyle.food.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Unit {
    ML("ml"),
    L("l"),
    G("g"),
    KG("kg"),
    SLICE("slice"),
    PIECE("piece");

    private final String value;
}
