package org.lia.models.dragon;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "dragon_head")
public class DragonHead {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @NotNull
    @Column(nullable = false)
    private Integer eyesCount;
}
