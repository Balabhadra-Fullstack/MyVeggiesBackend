package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierPaymentResponseDTO {
    private Long paymentId;
    private Long supplierId;
    private String supplierName;
    private BigDecimal amount;
    private String method;
    private LocalDate dateTime;
}
