package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseResponseDTO {
   private Long purchaseId;

private String vehicleNumber;
private String suppliername;
private LocalDate entryDate;

private BigDecimal truckWeight;

private List<PurcahseItemResDTO> purchaseItems;

private BigDecimal netAmount;

}
