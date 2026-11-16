package com.fresh.veggies.DTO;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemDTO {

    @NotNull(message = "Vegetable id is required")
    @Positive(message = "Vegetable id must be positive")
    private Long vegetableId;

    @NotNull(message = "Purchase quantity is required")
    @Positive(message = "Purchase quantity must be greater than zero")
    private BigDecimal kg;

    @NotNull(message = "Purchase price is required")
    @Positive(message = "Purchase price must be greater than zero")
    private BigDecimal purchasePrice;

    public PurchaseItemDTO(Long vegetableId, BigDecimal kg, BigDecimal purchasePrice) {
        this.vegetableId = vegetableId;
        this.kg = kg;
        this.purchasePrice = purchasePrice;
    }
}