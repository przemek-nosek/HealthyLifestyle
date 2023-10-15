package pl.healthylifestyle.userservice.entity;


import java.util.List;

public class AdditionalUserData {

    private Long id;
    private String userUuid;
    private int age;
    private Gender gender;
    private List<Allergen> allergens;
    private List<Measurement> measurements;
}
