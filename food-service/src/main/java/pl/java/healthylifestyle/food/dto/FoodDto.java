package pl.java.healthylifestyle.food.dto;

import lombok.Builder;
import pl.java.healthylifestyle.food.entity.Allergen;
import pl.java.healthylifestyle.food.entity.Unit;

import java.util.Set;

@Builder
public record FoodDto(
        String uuid,
        String name,
        double energy,
        double carbohydrate,
        double sugar,
        double protein,
        double fat,
        double saturatedFat,
        double fiber,
        double salt,
        double size,
        Unit unit,
        String producer,
        Set<Allergen> allergens
) {
}
