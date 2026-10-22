package com.fresh.veggies.Entity;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
public class VegCategory {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    public VegCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
