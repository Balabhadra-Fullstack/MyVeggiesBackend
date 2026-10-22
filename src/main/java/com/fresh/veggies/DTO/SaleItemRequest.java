package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SaleItemRequest {
    private Long vegetableId;
    private BigDecimal kg;
    private BigDecimal salePrice;

    public SaleItemRequest(Long vegetableId, BigDecimal kg, BigDecimal salePrice) {
        this.vegetableId = vegetableId;
        this.kg = kg;
        this.salePrice = salePrice;
    }
}
