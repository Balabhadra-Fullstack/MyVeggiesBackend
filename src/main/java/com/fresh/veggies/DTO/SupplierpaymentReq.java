package com.fresh.veggies.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierpaymentReq {
    private Long id;
    private BigDecimal paymentamount;
    private String paymentmethod;
    private LocalDate paymenttime;
}