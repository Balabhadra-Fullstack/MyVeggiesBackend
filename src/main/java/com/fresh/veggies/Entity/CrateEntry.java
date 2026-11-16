package com.fresh.veggies.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CrateEntry {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @NotNull(message = "Crate quantity is required")
 @Positive(message = "Crate quantity must be greater than zero")
 private BigDecimal quantity;
 @NotNull(message = "Entry date is required")
 private LocalDate entryDate;
 @ManyToOne
 @JoinColumn(name= "customer_id", nullable = false)
 @NotNull(message = "Customer is required")
 private Customer customer;
 @OneToOne
 @JoinColumn(name = "sale_id", unique = true)
 @JsonIgnore
 private Sale sale;
}
