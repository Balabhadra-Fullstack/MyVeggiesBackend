
package com.fresh.veggies.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
        name = "idx_commission_purchase_supplier_date",
        columnList = "supplier_id, purchase_date"))
public class CommissionPurchase {
@GeneratedValue(strategy = GenerationType.IDENTITY) 
 @Id 
private Long id;
@ManyToOne
@JoinColumn (name = "supplier_id")
private Supplier supplier;
private String truckNumber;
@OneToMany(mappedBy = "commissionPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
private List<CommissionPurchaseItems> items = new ArrayList<>();
private BigDecimal commissionRate;
private BigDecimal truckCharges;
private BigDecimal laborCharges;
private LocalDate purchaseDate;
private BigDecimal otherCharges;
private BigDecimal supplierpayableAmount;    
}