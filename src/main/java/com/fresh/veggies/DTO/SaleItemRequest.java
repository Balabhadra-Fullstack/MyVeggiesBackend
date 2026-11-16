package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SaleItemRequest {
    @NotNull(message = "Vegetable id is required")
    @Positive(message = "Vegetable id must be positive")
    private Long vegetableId;

    @NotNull(message = "Sale quantity is required")
    @Positive(message = "Sale quantity must be greater than zero")
    private BigDecimal kg;

    @NotNull(message = "Sale price is required")
    @Positive(message = "Sale price must be greater than zero")
    private BigDecimal salePrice;

    public SaleItemRequest(Long vegetableId, BigDecimal kg, BigDecimal salePrice) {
        this.vegetableId = vegetableId;
        this.kg = kg;
        this.salePrice = salePrice;
    }
}
