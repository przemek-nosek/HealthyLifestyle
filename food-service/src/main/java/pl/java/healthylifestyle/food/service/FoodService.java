package pl.java.healthylifestyle.food.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.java.healthylifestyle.food.dto.FoodDto;
import pl.java.healthylifestyle.food.dto.FoodResponse;
import pl.java.healthylifestyle.food.dto.UpdateFoodRequest;
import pl.java.healthylifestyle.food.entity.Food;
import pl.java.healthylifestyle.food.exception.FoodAlreadyExistsException;
import pl.java.healthylifestyle.food.exception.FoodNotFoundException;
import pl.java.healthylifestyle.food.mapper.FoodMapper;
import pl.java.healthylifestyle.food.repository.FoodRepository;

import static pl.java.healthylifestyle.food.exception.message.FoodExceptionMessage.FOOD_ALREADY_EXISTS_ERROR;
import static pl.java.healthylifestyle.food.exception.message.FoodExceptionMessage.FOOD_NOT_FOUND_ERROR;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public Page<FoodResponse> findAll(Pageable pageable, String name) {
        String noAccentName = StringUtils.stripAccents(name).toLowerCase();

        return foodRepository.findAllByNameContainingIgnoringCaseAndAccent(pageable, noAccentName)
                .map(foodMapper::toFoodResponse);
    }

    public FoodResponse findByUuid(String uuid) {
        return foodRepository.findByUuid(uuid)
                .map(foodMapper::toFoodResponse)
                .orElseThrow(() -> handleFoodNotFound(uuid));
    }

    public FoodResponse createFood(FoodDto foodDto) {
        foodRepository.findByNameIgnoreCaseAndSize(foodDto.name(), foodDto.size())
                .ifPresent(this::handleFoodAlreadyExists);

        Food savedFood = foodRepository.save(foodMapper.toFood(foodDto));

        return foodMapper.toFoodResponse(savedFood);
    }

    public FoodResponse updateFood(FoodDto foodDto) {
        //todo if status verified & current role user

        Food food = foodRepository.findByUuid(foodDto.uuid())
                .map(existingFood -> foodMapper.updateFood(foodDto, existingFood))
                .orElseThrow(() -> handleFoodNotFound(foodDto.uuid()));

        return foodMapper.toFoodResponse(food);
    }

    public FoodResponse verifyFood(String uuid, String loggedUserUuid) {
        Food verifiedFood = foodRepository.findByUuid(uuid)
                .map(food -> {
                    food.setVerified(true);
                    food.setVerifiedBy(loggedUserUuid);
                    return foodRepository.save(food);
                })
                .orElseThrow(() -> handleFoodNotFound(uuid));
        return foodMapper.toFoodResponse(verifiedFood);
    }

    private void handleFoodAlreadyExists(Food food) {
        throw new FoodAlreadyExistsException(
                String.format(FOOD_ALREADY_EXISTS_ERROR.getMessage(), food.getName(), food.getSize()), FOOD_ALREADY_EXISTS_ERROR.name());
    }

    private FoodNotFoundException handleFoodNotFound(String uuid) {
        return new FoodNotFoundException(String.format(FOOD_NOT_FOUND_ERROR.getMessage(), uuid), FOOD_NOT_FOUND_ERROR.name());
    }
}
