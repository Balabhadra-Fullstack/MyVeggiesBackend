
package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleRequest {

    private Long customerId;
    private LocalDate saleDate;
    private List<SaleItemRequest> saleItems = new ArrayList<>();
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
