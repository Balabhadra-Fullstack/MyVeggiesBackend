package com.fresh.veggies.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(indexes = @Index(name = "idx_supplier_payment_supplier_date", columnList = "supplier_id, payment_date_time"))
@Getter
@Setter
@NoArgsConstructor
public class SupplierPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @ManyToOne
    @JoinColumn(name = "supplier_id",nullable = false)
    @NotNull(message = "Supplier is required")
    private Supplier supplier;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal paymentAmount;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDateTime;

    public SupplierPayment(Long id, Supplier supplier, BigDecimal paymentAmount, String paymentMethod, LocalDate paymentDateTime) {
        this.id = id;
        this.supplier = supplier;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDateTime = paymentDateTime;
    }
}