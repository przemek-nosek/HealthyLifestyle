package pl.healthylifestyle.userservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.healthylifestyle.userservice.entity.Allergen;
import pl.healthylifestyle.userservice.entity.Gender;

import java.util.List;
import java.util.Set;

@Builder
public record UpdateAdditionalUserDataRequest(
        String phoneNumber,
        @Min(value = 13, message = "Age can't be lower than {value}.")
        @Max(value = 100, message = "Age can't be higher than {value}.")
        int age,
        @NotNull(message = "Gender can't be null.")
        Gender gender,
        Set<Allergen> allergens
) {
}
