package pl.healthylifestyle.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private double weight;
    private double calfSize;
    private double thighSize;
    private double waistSize;
    private double chestSize;
    private double neckSize;
    private double forearmSize;
    private double bicepsSize;
    private LocalDate measurementDate;
    private String userUuid;
}
