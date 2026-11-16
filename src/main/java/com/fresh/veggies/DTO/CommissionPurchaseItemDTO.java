package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class CommissionPurchaseItemDTO {
    @NotNull(message = "Vegetable id is required")
    @Positive(message = "Vegetable id must be positive")
private Long vegetableId;

@NotNull(message = "Commission quantity is required")
@Positive(message = "Commission quantity must be greater than zero")
private BigDecimal quantity;

@NotNull(message = "Commission rate is required")
@Positive(message = "Commission rate must be greater than zero")
private BigDecimal rate;

private BigDecimal amount;
}
