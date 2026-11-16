package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SaleItemResDTO {
   private Long vegetableId;
   private String vegetableName;
private BigDecimal kg;
private BigDecimal salePrice;
private BigDecimal totalAmount;

   public SaleItemResDTO(Long vegetableId, String vegetableName, BigDecimal kg, BigDecimal salePrice, BigDecimal totalAmount) {
      this.vegetableId = vegetableId;
      this.vegetableName = vegetableName;
      this.kg = kg;
      this.salePrice = salePrice;
      this.totalAmount = totalAmount;
   }
}
