package com.fresh.veggies.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
public class VegCategory {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;
    
    public VegCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
