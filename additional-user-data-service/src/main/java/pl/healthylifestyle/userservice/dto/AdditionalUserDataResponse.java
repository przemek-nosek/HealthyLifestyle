package pl.healthylifestyle.userservice.dto;

import lombok.Builder;
import pl.healthylifestyle.userservice.entity.Allergen;
import pl.healthylifestyle.userservice.entity.Gender;

import java.util.List;
import java.util.Set;

@Builder
public record AdditionalUserDataResponse(
        String uuid,
        String phoneNumber,
        int age,
        Gender gender,
        Set<Allergen> allergens
) {
}
