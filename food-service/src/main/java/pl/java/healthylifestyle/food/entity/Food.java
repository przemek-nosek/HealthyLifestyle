package pl.java.healthylifestyle.food.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.java.healthylifestyle.food.entity.converter.AllergenEnumSetToStringConverter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;
    private double energy;
    private double carbohydrate;
    private double sugar;
    private double protein;
    private double fat;
    private double saturatedFat;
    private double fiber;
    private double salt;
    private double size;
    private Unit unit;
    private String producer;
    @Convert(converter = AllergenEnumSetToStringConverter.class)
    private Set<Allergen> allergens;
    private boolean verified;
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    private String verifiedBy;


    //todo: byte[] image of a product
}
