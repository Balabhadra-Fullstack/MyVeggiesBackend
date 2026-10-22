package com.fresh.veggies.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemDTO {

    private Long vegetableId;
    private BigDecimal kg;

    private BigDecimal purchasePrice;

    public PurchaseItemDTO(Long vegetableId, BigDecimal kg, BigDecimal purchasePrice) {
        this.vegetableId = vegetableId;
        this.kg = kg;
        this.purchasePrice = purchasePrice;
    }
}