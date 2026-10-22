package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierMoneyCalculationRes {
    
    public String Suppliername;
    public Long id;
    public String mobileNumber;
    public BigDecimal TotalAmountPurchaesd;
    public BigDecimal TotalAmountPaidByme;
    public BigDecimal DueAmount;
      public SupplierMoneyCalculationRes(
             
            String Suppliername,
            Long id,
            String mobileNumber,
            BigDecimal TotalAmountPurchaesd,
            BigDecimal TotalAmountPaidByme,
            BigDecimal DueAmount) {
            
        this.Suppliername = Suppliername;
        this.id=id;
        this.mobileNumber=mobileNumber;
        this.TotalAmountPurchaesd = TotalAmountPurchaesd;
        this.TotalAmountPaidByme = TotalAmountPaidByme;
        this.DueAmount = DueAmount;
    }
}
