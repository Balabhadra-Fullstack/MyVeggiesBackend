package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierpaymentReq {
    @NotNull(message = "Supplier id is required")
    @Positive(message = "Supplier id must be positive")
    private Long id;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal paymentamount;

    @NotBlank(message = "Payment method is required")
    private String paymentmethod;

    @NotNull(message = "Payment date is required")
    private LocalDate paymenttime;
}