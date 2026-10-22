package com.fresh.veggies.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerMoneyCalculationRes {

    private String Customername;
    private Long id;
    private String mobileNumber;
    private BigDecimal totalAmountSold;
    private BigDecimal totalAmountPaidByCustomer;
    private BigDecimal dueAmount;

    public CustomerMoneyCalculationRes(
            String Customername,
            Long id,
            String mobileNumber,
            BigDecimal totalAmountSold,
            BigDecimal totalAmountPaidByCustomer,
            BigDecimal dueAmount) {
        this.Customername = Customername;
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.totalAmountSold = totalAmountSold;
        this.totalAmountPaidByCustomer = totalAmountPaidByCustomer;
        this.dueAmount = dueAmount;
    }
}