package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class CommissionPurchaseItemDTO {
private Long vegetableId;
private BigDecimal quantity;
private BigDecimal rate;
private BigDecimal amount;
}
