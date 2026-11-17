package com.fresh.veggies.Entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table(indexes = @Index(
        name = "idx_commission_purchase_items_purchase",
        columnList = "commission_purchase_id"))
public class CommissionPurchaseItems {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne 
@JoinColumn (name = "commission_purchase_id")
@JsonIgnore 
private CommissionPurchase commissionPurchase;
@ManyToOne 
@JoinColumn (name = "vegetable_id")
private Vegetable vegetable;
private BigDecimal quantity;
private BigDecimal rate;
private BigDecimal amount;
}
