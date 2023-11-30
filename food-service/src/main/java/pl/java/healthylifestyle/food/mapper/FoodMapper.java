package pl.java.healthylifestyle.food.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.java.healthylifestyle.food.dto.CreateFoodRequest;
import pl.java.healthylifestyle.food.dto.FoodDto;
import pl.java.healthylifestyle.food.dto.FoodResponse;
import pl.java.healthylifestyle.food.entity.Food;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface FoodMapper {

    FoodResponse toFoodResponse(Food source);

    @Mapping(target = "uuid", expression = "java(UUID.randomUUID().toString())")
    FoodDto toFoodDto(CreateFoodRequest source);

    Food toFood(FoodDto source);
}
