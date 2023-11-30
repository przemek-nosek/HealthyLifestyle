package pl.java.healthylifestyle.food.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.java.healthylifestyle.food.dto.FoodDto;
import pl.java.healthylifestyle.food.dto.FoodResponse;
import pl.java.healthylifestyle.food.entity.Food;
import pl.java.healthylifestyle.food.exception.FoodNotFoundException;
import pl.java.healthylifestyle.food.mapper.FoodMapper;
import pl.java.healthylifestyle.food.repository.FoodRepository;

import static pl.java.healthylifestyle.food.exception.message.FoodExceptionMessage.FOOD_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public Page<FoodResponse> findAll(Pageable pageable) {
        String ryż = StringUtils.stripAccents("ę€óąśłżźćń");
        System.out.println(ryż);
        return foodRepository.findAll(pageable)
                .map(foodMapper::toFoodResponse);
    }

    public FoodResponse createFood(FoodDto foodDto) {
        Food food = foodMapper.toFood(foodDto);

        Food savedFood = foodRepository.save(food);

        return foodMapper.toFoodResponse(savedFood);
    }

    public FoodResponse verifyFood(String uuid) {
        Food verifiedFood = foodRepository.findByUuid(uuid)
                .map(food -> {
                    food.setVerified(true);
                    return foodRepository.save(food);
                })
                .orElseThrow(() -> handleFoodNotFound(uuid));
        return foodMapper.toFoodResponse(verifiedFood);
    }

    private RuntimeException handleFoodNotFound(String uuid) {
        return new FoodNotFoundException(String.format(FOOD_NOT_FOUND_ERROR.getMessage(), uuid), FOOD_NOT_FOUND_ERROR.name());
    }

}
