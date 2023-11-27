package pl.java.healthylifestyle.food.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.java.healthylifestyle.food.entity.converter.EnumSetToStringConverter;

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
    @Convert(converter = EnumSetToStringConverter.class)
    private Set<Shop> shops;
}
