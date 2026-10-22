package com.fresh.veggies.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Customer customer;
    private BigDecimal paymentAmount;
    private String paymentMethod;
    private LocalDate paymentDateTime;

    public CustomerPayment(Long id, Customer customer, BigDecimal paymentAmount, String paymentMethod, LocalDate paymentDateTime) {
        this.id = id;
        this.customer = customer;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDateTime = paymentDateTime;
    }
}
