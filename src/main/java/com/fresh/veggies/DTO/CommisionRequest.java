package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class CommisionRequest {
    private Long supplierId;
    private String truckNumber;
    private LocalDate date;
    private BigDecimal commissionRate;
    private List<CommissionPurchaseItemDTO> items = new ArrayList<>();
    private BigDecimal truckcharges;
    private BigDecimal laborCharges;
    private BigDecimal otherCharges;
}
