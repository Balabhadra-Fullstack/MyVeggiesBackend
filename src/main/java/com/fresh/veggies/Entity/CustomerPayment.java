package com.fresh.veggies.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomerPayment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer;
    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal paymentAmount;
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    @NotNull(message = "Payment date is required")
    private LocalDate paymentDateTime;

    public CustomerPayment(Long id, Customer customer, BigDecimal paymentAmount, String paymentMethod, LocalDate paymentDateTime) {
        this.id = id;
        this.customer = customer;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDateTime = paymentDateTime;
    }
}
