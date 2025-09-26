package org.lia.models.utils;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double x;

    @Column
    private Float y;

    @NotNull
    @Column(nullable = false)
    private Float z;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    public Long getId() { return id; }
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Float getY() { return y; }
    public void setY(Float y) { this.y = y; }
    public Float getZ() { return z; }
    public void setZ(Float z) { this.z = z; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}
