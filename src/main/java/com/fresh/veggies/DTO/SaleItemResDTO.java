package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SaleItemResDTO {
   private String vegetableName;
private BigDecimal kg;
private BigDecimal salePrice;
private BigDecimal totalAmount;

   public SaleItemResDTO(String vegetableName, BigDecimal kg, BigDecimal salePrice, BigDecimal totalAmount) {
      this.vegetableName = vegetableName;
      this.kg = kg;
      this.salePrice = salePrice;
      this.totalAmount = totalAmount;
   }
}
