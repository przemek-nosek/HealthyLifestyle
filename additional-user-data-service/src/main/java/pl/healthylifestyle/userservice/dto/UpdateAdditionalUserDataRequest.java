package pl.healthylifestyle.userservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.healthylifestyle.userservice.entity.Allergen;
import pl.healthylifestyle.userservice.entity.Gender;

import java.util.List;

@Builder
public record UpdateAdditionalUserDataRequest(
        String phoneNumber,
        @Min(value = 13, message = "Age can't be lower than {value}.")
        int age,
        @NotNull(message = "Gender can't be null.")
        Gender gender,
        List<Allergen> allergens
        ) {
}
