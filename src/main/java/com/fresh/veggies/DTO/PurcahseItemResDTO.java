package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurcahseItemResDTO {
    private String vegetableName;
private BigDecimal kg;
private BigDecimal purchasePrice;
private BigDecimal totalAmount;
}
