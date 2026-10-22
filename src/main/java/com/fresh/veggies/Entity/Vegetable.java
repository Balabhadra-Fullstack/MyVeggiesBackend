package com.fresh.veggies.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vegetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

     private String odianame;

    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    @JsonIgnore
    private VegCategory category;

    public Vegetable(Long id, String name, String odianame, String image, VegCategory category) {
        this.id = id;
        this.name = name;
        this.odianame = odianame;
        this.image = image;
        this.category = category;
    }
}