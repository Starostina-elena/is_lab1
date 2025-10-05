package org.lia.models.person;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.lia.models.utils.Color;
import org.lia.models.utils.Country;

import org.lia.models.utils.Location;

@Entity
@Table(name = "person")
public class Person {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false)
    private Color eyeColor;

    @Setter
    @Getter
    @Column(nullable = false)
    private Color hairColor;

    @Setter
    @Getter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Setter
    @Getter
    @Column()
    @Min(value = 0, message = "Вес должен быть больше 0")
    private Long weight;

    @Setter
    @Getter
    @Column(nullable = false)
    private Country nationality;
}
