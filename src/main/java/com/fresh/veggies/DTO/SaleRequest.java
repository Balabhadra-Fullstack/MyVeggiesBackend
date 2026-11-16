
package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class SaleRequest {

    @NotNull(message = "Customer id is required")
    @Positive(message = "Customer id must be positive")
    private Long customerId;

    @NotNull(message = "Sale date is required")
    private LocalDate saleDate;

    @NotEmpty(message = "At least one sale item is required")
    @Valid
    private List<SaleItemRequest> saleItems = new ArrayList<>();

    @PositiveOrZero(message = "Crate count cannot be negative")
    private BigDecimal crateCount;

    public SaleRequest() {
    }

    public SaleRequest(Long customerId, LocalDate saleDate,
                       List<SaleItemRequest> saleItems,
                       BigDecimal crateCount) {
        this.customerId = customerId;
        this.saleDate = saleDate;
        this.saleItems = saleItems;
        this.crateCount = crateCount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public List<SaleItemRequest> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItemRequest> saleItems) {
        this.saleItems = saleItems;
    }

    public BigDecimal getCrateCount() {
        return crateCount;
    }

    public void setCrateCount(BigDecimal crateCount) {
        this.crateCount = crateCount;
    }
}
