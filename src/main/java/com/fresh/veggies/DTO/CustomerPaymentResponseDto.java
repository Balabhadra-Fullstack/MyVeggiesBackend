package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustomerPaymentResponseDto {
    private Long paymentId;
    private Long customerId;
    private String customerName;
    private BigDecimal amount;
    private String method;
    private LocalDate dateTime;
}
