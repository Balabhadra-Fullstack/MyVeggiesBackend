package com.fresh.veggies.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SupplierPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @ManyToOne
    @JoinColumn(name = "supplier_id",nullable = false)
    private Supplier supplier;

    private BigDecimal paymentAmount;

    private String paymentMethod;

    private LocalDate paymentDateTime;

    public SupplierPayment(Long id, Supplier supplier, BigDecimal paymentAmount, String paymentMethod, LocalDate paymentDateTime) {
        this.id = id;
        this.supplier = supplier;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDateTime = paymentDateTime;
    }
}