package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class crateReturnRequestDTO{
    private Long customerId;
    private BigDecimal quantity;
    private LocalDate returnDate;

    public crateReturnRequestDTO(Long customerId, BigDecimal quantity, LocalDate returnDate) {
        this.customerId = customerId;
        this.quantity = quantity;
        this.returnDate = returnDate;
    }
}