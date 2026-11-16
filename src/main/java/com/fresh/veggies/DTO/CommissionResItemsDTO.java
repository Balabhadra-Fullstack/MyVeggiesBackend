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
public class CommissionResItemsDTO {
    public String vegetablename;
    public BigDecimal quantity;
    public BigDecimal rate;
    public BigDecimal amount;
}
