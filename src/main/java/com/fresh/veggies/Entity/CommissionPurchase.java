
package com.fresh.veggies.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
public class CommissionPurchase {
@GeneratedValue(strategy = GenerationType.IDENTITY) 
 @Id 
private Long id;
@ManyToAny 
@JoinColumn (name = "supplier_id")
private Supplier supplier;
@OneToMany ( mappedBy = "CommissionPurchase" ,cascade = CascadeType.ALL, orphanRemoval = true)
private List<CommissionPurchaseItems> List= new ArrayList<>();
private BigDecimal commissionRate;
private BigDecimal truckCharges;
private BigDecimal laborCharges;
private LocalDate purchaseDate;
private BigDecimal otherCharges;
private BigDecimal supplierpayableAmount;    
}