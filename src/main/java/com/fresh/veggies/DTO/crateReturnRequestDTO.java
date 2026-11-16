package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class crateReturnRequestDTO{
    @NotNull(message = "Customer id is required")
    @Positive(message = "Customer id must be positive")
    private Long customerId;

    @NotNull(message = "Return quantity is required")
    @Positive(message = "Return quantity must be greater than zero")
    private BigDecimal quantity;

    @NotNull(message = "Return date is required")
    private LocalDate returnDate;

    public crateReturnRequestDTO(Long customerId, BigDecimal quantity, LocalDate returnDate) {
        this.customerId = customerId;
        this.quantity = quantity;
        this.returnDate = returnDate;
    }
}