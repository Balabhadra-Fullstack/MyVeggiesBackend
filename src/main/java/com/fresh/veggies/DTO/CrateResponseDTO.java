package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CrateResponseDTO {
    private String Customername;
    private BigDecimal CrateTaken;
    private BigDecimal CrateReturned;
    private BigDecimal CrateDue;
    public CrateResponseDTO(String customername, BigDecimal crateTaken, BigDecimal crateReturned, BigDecimal crateDue) {
        Customername = customername;
        CrateTaken = crateTaken;
        CrateReturned = crateReturned;
        CrateDue = crateDue;
    }
    
}
