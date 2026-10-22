package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerpaymentReq {
    private Long id;
    private BigDecimal paymentamount;
    private String paymentmethod;
    private LocalDate paymenttime;

    public CustomerpaymentReq(Long id, BigDecimal paymentamount, String paymentmethod, LocalDate paymenttime) {
        this.id = id;
        this.paymentamount = paymentamount;
        this.paymentmethod = paymentmethod;
        this.paymenttime = paymenttime;
    }
}
