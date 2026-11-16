package com.fresh.veggies.Entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
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
