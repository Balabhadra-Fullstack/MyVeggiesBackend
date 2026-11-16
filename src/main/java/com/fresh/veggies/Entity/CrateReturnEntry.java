package com.fresh.veggies.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CrateReturnEntry {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Return quantity is required")
    @Positive(message = "Return quantity must be greater than zero")
    private BigDecimal quantity;
    @NotNull(message = "Return date is required")
    private LocalDate returnDate;
    @ManyToOne
    @JoinColumn(name= "customer_id", nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer;
}
