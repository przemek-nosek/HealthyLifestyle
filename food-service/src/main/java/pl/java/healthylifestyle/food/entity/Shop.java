package pl.java.healthylifestyle.food.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Shop {
    BIEDRONKA("Biedronka"),
    AUCHAN("Auchan"),
    LIDL("Lidl");

    private final String value;
}
