package pl.healthylifestyle.userservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.healthylifestyle.userservice.entity.converter.EnumListToStringConverter;

import java.util.List;

//@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalUserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userUuid;
    private String phoneNumber;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Convert(converter = EnumListToStringConverter.class)
    private List<Allergen> allergens;
    private List<Measurement> measurements;
}
