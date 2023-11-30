package pl.java.healthylifestyle.food.dto;

import lombok.Builder;
import pl.java.healthylifestyle.food.entity.Allergen;
import pl.java.healthylifestyle.food.entity.Shop;

import java.util.Set;

@Builder
public record CreateFoodRequest(
        String name,
        double energy,
        double carbohydrate,
        double sugar,
        double protein,
        double fat,
        double saturatedFat,
        double fiber,
        double salt,
        String producer,
        Set<Shop> shops,
        Set<Allergen> allergens
) {
}
