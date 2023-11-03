package pl.healthylifestyle.userservice.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.healthylifestyle.userservice.entity.Allergen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class EnumListToStringConverter implements AttributeConverter<List<Allergen>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<Allergen> attribute) {
        return attribute.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<Allergen> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER)).map(Allergen::valueOf).collect(Collectors.toList());
    }
}
