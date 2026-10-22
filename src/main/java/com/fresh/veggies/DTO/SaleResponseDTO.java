package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor

public class SaleResponseDTO {
    private Long saleId;
    private String customername;
    private LocalDate saleDate;
     private List<SaleItemResDTO> saleItems;
     private BigDecimal totalsaleAmount;

    public SaleResponseDTO(Long saleId, String customername, LocalDate saleDate, List<SaleItemResDTO> saleItems, BigDecimal totalsaleAmount) {
        this.saleId = saleId;
        this.customername = customername;
        this.saleDate = saleDate;
        this.saleItems = saleItems;
        this.totalsaleAmount = totalsaleAmount;
    }
}
