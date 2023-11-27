package pl.java.healthylifestyle.food.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.java.healthylifestyle.food.entity.converter.AllergenEnumSetToStringConverter;
import pl.java.healthylifestyle.food.entity.converter.ShopEnumSetToStringConverter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private double energy;
    private double carbohydrate;
    private double sugar;
    private double protein;
    private double fat;
    private double saturatedFat;
    private double fiber;
    private double salt;
    private String producer;
    @Convert(converter = ShopEnumSetToStringConverter.class)
    private Set<Shop> shops;
    @Convert(converter = AllergenEnumSetToStringConverter.class)
    private Set<Allergen> allergens;
}
