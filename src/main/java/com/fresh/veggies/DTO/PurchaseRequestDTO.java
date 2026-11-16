package com.fresh.veggies.DTO;


import lombok.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseRequestDTO {

    @NotNull(message = "Supplier id is required")
    @Positive(message = "Supplier id must be positive")
    private Long supplierId;

    @NotBlank(message = "Vehicle name is required")
    private String vehiclename;

    @NotNull(message = "Entry date is required")
    private LocalDate entryDateTime;

    @NotNull(message = "Truck weight is required")
    @Positive(message = "Truck weight must be greater than zero")
    private BigDecimal truckWeight;

    @NotEmpty(message = "At least one purchase item is required")
    @Valid
    private List<PurchaseItemDTO> purchaseItems;

    public PurchaseRequestDTO(Long supplierId, String vehiclename, LocalDate entryDateTime, BigDecimal truckWeight, List<PurchaseItemDTO> purchaseItems) {
        this.supplierId = supplierId;
        this.vehiclename = vehiclename;
        this.entryDateTime = entryDateTime;
        this.truckWeight = truckWeight;
        this.purchaseItems = purchaseItems;
    }
}