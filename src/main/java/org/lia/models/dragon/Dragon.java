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
    @OneToOne(optional = false)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @Setter
    @Getter
    @NotNull
    @Column(nullable = false)
    @CreationTimestamp
    private java.time.ZonedDateTime creationDate;

    @Setter
    @Getter
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "dragon_cave_id", nullable = false)
    private DragonCave cave;

    @Setter
    @Getter
    @ManyToOne()
    @JoinColumn(name = "killer_id")
    private Person killer; //Поле может быть null

    @Setter
    @Getter
    @Column()
    @Min(value = 0, message = "Возраст должен быть больше 0")
    private long age;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "color")
    private Color color;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "dragon_type")
    private DragonType type;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "dragon_character")
    private DragonCharacter character;

    @Setter
    @Getter
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "dragon_head_id", nullable = false)
    private DragonHead head;
}
