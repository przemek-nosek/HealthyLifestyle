package pl.healthylifestyle.userservice.entity;


import jakarta.persistence.*;
import lombok.*;
import pl.healthylifestyle.userservice.entity.converter.EnumSetToStringConverter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalUserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String phoneNumber;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Convert(converter = EnumSetToStringConverter.class)
    private Set<Allergen> allergens;
}
