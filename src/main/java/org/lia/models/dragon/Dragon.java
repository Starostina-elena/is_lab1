package org.lia.models.dragon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.lia.models.person.Person;
import org.lia.models.utils.Coordinates;

import org.lia.models.utils.Color;
import org.lia.models.utils.DragonCharacter;
import org.lia.models.utils.DragonType;
import org.lia.models.person.Person;

@Entity
@Table(name = "dragon")
public class Dragon {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Значение id должно быть больше 0")
    private long id;

    @Setter
    @Getter
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @Setter
    @Getter
    @Column(nullable = false)
    @CreationTimestamp
    private java.time.ZonedDateTime creationDate;

    @Setter
    @Getter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "dragon_cave_id", nullable = false)
    private DragonCave cave;

    @Setter
    @Getter
    @ManyToOne()
    @JoinColumn(name = "killer_id")
    private Person killer;

    @Setter
    @Getter
    @Column()
    @Min(value = 0, message = "Возраст должен быть больше 0")
    private Long age;

    @Setter
    @Getter
    @Column(nullable = false)
    private Color color;

    @Setter
    @Getter
    @Column(nullable = false)
    private DragonType type;

    @Setter
    @Getter
    @Column(nullable = false)
    private DragonCharacter character;

    @Setter
    @Getter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "dragon_head_id", nullable = false)
    private DragonHead head;
}
