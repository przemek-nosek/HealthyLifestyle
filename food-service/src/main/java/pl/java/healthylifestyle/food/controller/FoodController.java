package pl.java.healthylifestyle.food.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.java.healthylifestyle.food.dto.CreateFoodRequest;
import pl.java.healthylifestyle.food.dto.FoodDto;
import pl.java.healthylifestyle.food.dto.FoodResponse;
import pl.java.healthylifestyle.food.dto.UpdateFoodRequest;
import pl.java.healthylifestyle.food.mapper.FoodMapper;
import pl.java.healthylifestyle.food.service.FoodService;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final FoodMapper foodMapper;

    @GetMapping
    ResponseEntity<Page<FoodResponse>> findAll(
            @PageableDefault(size = 30) Pageable pageable,
            @RequestParam(defaultValue = "") String name) {
        return ResponseEntity.ok(foodService.findAll(pageable, name));
    }

    @GetMapping("/{uuid}")
    ResponseEntity<FoodResponse> findByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(foodService.findByUuid(uuid));
    }

    @PostMapping
    @PreAuthorize("hasRole('user')")
    ResponseEntity<FoodResponse> createFood(@RequestBody @Valid CreateFoodRequest request) {
        FoodDto foodDto = foodMapper.toFoodDto(request);
        return new ResponseEntity<>(foodService.createFood(foodDto), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('user', 'employee')")
    ResponseEntity<FoodResponse> editFood(
            @PathVariable String uuid,
            @RequestBody @Valid UpdateFoodRequest request) {
        FoodDto foodDto = foodMapper.toFoodDto(uuid, request);
        return ResponseEntity.ok(foodService.updateFood(foodDto));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('employee')")
    ResponseEntity<FoodResponse> verifyFood(
            @PathVariable String uuid,
            JwtAuthenticationToken jwt) {
        return ResponseEntity.ok(foodService.verifyFood(uuid, jwt.getName()));
    }
}