package pl.healthylifestyle.userservice.entity;


import jakarta.persistence.*;
import lombok.*;
import pl.healthylifestyle.userservice.entity.converter.EnumListToStringConverter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalUserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String phoneNumber;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Convert(converter = EnumListToStringConverter.class)
    private List<Allergen> allergens;
}
