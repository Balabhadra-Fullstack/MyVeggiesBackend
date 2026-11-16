package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter 
@Setter 
public class SaleResponseWithCrate {
     private Long saleId;
     private Long customerId;
    private String customername;
    private LocalDate saleDate;
     private List<SaleItemResDTO> saleItems;
     private BigDecimal crateCount;
     private BigDecimal totalsaleAmount;
}
