package com.fresh.veggies.Entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class SaleItems {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "Sale_id",nullable = false)
    @JsonIgnore
    private Sale sale;
    @ManyToOne
    @JoinColumn(name = "vegitable_id",nullable = false)
    private Vegetable vegitable;
    private BigDecimal kg;
    private BigDecimal price;
    private BigDecimal Totalunitprice;

    public SaleItems(Long id, Sale sale, Vegetable vegitable, BigDecimal kg, BigDecimal price, BigDecimal totalunitprice) {
        this.id = id;
        this.sale = sale;
        this.vegitable = vegitable;
        this.kg = kg;
        this.price = price;
        this.Totalunitprice = totalunitprice;
    }
}
