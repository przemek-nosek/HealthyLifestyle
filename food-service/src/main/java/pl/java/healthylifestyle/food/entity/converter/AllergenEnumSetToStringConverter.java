package pl.java.healthylifestyle.food.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.java.healthylifestyle.food.entity.Allergen;
import pl.java.healthylifestyle.food.entity.Shop;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class AllergenEnumSetToStringConverter implements AttributeConverter<Set<Allergen>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<Allergen> attribute) {
        return attribute.stream()
                .map(Allergen::getValue)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<Allergen> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER)).map(Allergen::valueOf).collect(Collectors.toSet());
    }
}
