package com.fresh.veggies.DTO;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseRequestDTO {

    private Long supplierId;

    private String vehiclename;

    private LocalDate entryDateTime;

    private BigDecimal truckWeight;

    private List<PurchaseItemDTO> purchaseItems;

    public PurchaseRequestDTO(Long supplierId, String vehiclename, LocalDate entryDateTime, BigDecimal truckWeight, List<PurchaseItemDTO> purchaseItems) {
        this.supplierId = supplierId;
        this.vehiclename = vehiclename;
        this.entryDateTime = entryDateTime;
        this.truckWeight = truckWeight;
        this.purchaseItems = purchaseItems;
    }
}