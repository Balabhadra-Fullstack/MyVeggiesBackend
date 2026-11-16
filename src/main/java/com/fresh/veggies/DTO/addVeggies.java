package com.fresh.veggies.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class addVeggies {
    public interface FullRequest {}

    @NotBlank(message = "Vegetable name is required", groups = FullRequest.class)
    private String name;
    private String odianame;
    private String image;

    @NotNull(message = "Category id is required", groups = FullRequest.class)
    @Positive(message = "Category id must be positive")
    private Long category_id;
    
}
