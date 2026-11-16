package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
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
public class CommisionRequest {
    @NotNull(message = "Supplier id is required")
    @Positive(message = "Supplier id must be positive")
    private Long supplierId;
    private String truckNumber;

    @NotNull(message = "Purchase date is required")
    private LocalDate date;

    @DecimalMin(value = "0.0", message = "Commission rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Commission rate cannot exceed 100")
    private BigDecimal commissionRate;

    @NotEmpty(message = "At least one commission item is required")
    @Valid
    private List<CommissionPurchaseItemDTO> items = new ArrayList<>();

    @DecimalMin(value = "0.0", message = "Truck charges cannot be negative")
    private BigDecimal truckcharges;

    @DecimalMin(value = "0.0", message = "Labor charges cannot be negative")
    private BigDecimal laborCharges;

    @DecimalMin(value = "0.0", message = "Other charges cannot be negative")
    private BigDecimal otherCharges;
}
