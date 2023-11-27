package pl.java.healthylifestyle.food.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.java.healthylifestyle.food.entity.Shop;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class EnumSetToStringConverter implements AttributeConverter<Set<Shop>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<Shop> attribute) {
        return attribute.stream()
                .map(Shop::getValue)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<Shop> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER)).map(Shop::valueOf).collect(Collectors.toSet());
    }
}
