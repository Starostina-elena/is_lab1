package org.lia.models.utils;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "coordinates")
public class Coordinates {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @NotNull
    @Column(nullable = false)
    @Min(value = -842, message = "Значение X должно быть больше -842")
    private Integer x;

    @Setter
    @Getter
    @NotNull
    @Column(nullable = false)
    @Max(value = 154, message = "Значение Y должно быть меньше 154")
    private Long y;

}
