package pl.healthylifestyle.userservice.dto;

import pl.healthylifestyle.userservice.entity.Allergen;
import pl.healthylifestyle.userservice.entity.Gender;

import java.util.List;


public record AdditionalUserDataDto(String uuid,
                                    String phoneNumber,
                                    int age,
                                    Gender gender,
                                    List<Allergen> allergens) {
}