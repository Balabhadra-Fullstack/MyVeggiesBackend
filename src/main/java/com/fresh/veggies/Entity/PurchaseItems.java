package com.fresh.veggies.Entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(indexes = @Index(name = "idx_purchase_items_purchase", columnList = "purchase_id"))
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    @JsonIgnore
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "vegetable_id")
    private Vegetable vegetable;

    private BigDecimal kg;

    private BigDecimal purchasePrice;

    private BigDecimal totalAmount;

    public PurchaseItems(Long id, Purchase purchase, Vegetable vegetable, BigDecimal kg, BigDecimal purchasePrice, BigDecimal totalAmount) {
        this.id = id;
        this.purchase = purchase;
        this.vegetable = vegetable;
        this.kg = kg;
        this.purchasePrice = purchasePrice;
        this.totalAmount = totalAmount;
    }
    
}
