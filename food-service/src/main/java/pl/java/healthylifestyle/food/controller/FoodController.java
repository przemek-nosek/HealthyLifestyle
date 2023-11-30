package pl.java.healthylifestyle.food.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.java.healthylifestyle.food.dto.CreateFoodRequest;
import pl.java.healthylifestyle.food.dto.FoodDto;
import pl.java.healthylifestyle.food.dto.FoodResponse;
import pl.java.healthylifestyle.food.mapper.FoodMapper;
import pl.java.healthylifestyle.food.service.FoodService;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final FoodMapper foodMapper;

    @GetMapping
    ResponseEntity<Page<FoodResponse>> findAll(@PageableDefault(size = 30) Pageable pageable) {
        return ResponseEntity.ok(foodService.findAll(pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('user')")
    ResponseEntity<FoodResponse> createFood(@RequestBody CreateFoodRequest request) {
        FoodDto foodDto = foodMapper.toFoodDto(request);
        return new ResponseEntity<>(foodService.createFood(foodDto), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('employee')")
    ResponseEntity<FoodResponse> verifyFood(@PathVariable String uuid) {
        return ResponseEntity.ok(foodService.verifyFood(uuid));
    }
}