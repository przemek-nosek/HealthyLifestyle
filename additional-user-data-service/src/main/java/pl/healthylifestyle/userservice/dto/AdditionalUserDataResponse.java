package pl.healthylifestyle.userservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.healthylifestyle.userservice.entity.Allergen;
import pl.healthylifestyle.userservice.entity.Gender;

import java.util.List;

@Builder
public record AdditionalUserDataResponse(
        String uuid,
        String phoneNumber,
        int age,
        Gender gender,
        List<Allergen> allergens
        ) {
}
