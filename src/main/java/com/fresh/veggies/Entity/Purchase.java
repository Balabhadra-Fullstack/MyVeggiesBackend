package com.fresh.veggies.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_purchase_supplier_date", columnList = "supplier_id, entry_date"))
@Getter
@Setter
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate entryDate;
    private BigDecimal truckWeight;

    @ManyToOne
    @JoinColumn(name = "supplier_id",nullable = false)
    private Supplier supplier;

    private String vehiclename;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
private List<PurchaseItems> purchaseItems = new ArrayList<>();

private BigDecimal netAmount;

    public Purchase(Long id, LocalDate entryDate, BigDecimal truckWeight, Supplier supplier,
                    String vehiclename, List<PurchaseItems> purchaseItems, BigDecimal netAmount) {
        this.id = id;
        this.entryDate = entryDate;
        this.truckWeight = truckWeight;
        this.supplier = supplier;
        this.vehiclename = vehiclename;
        this.purchaseItems = purchaseItems;
        this.netAmount = netAmount;
    }
    
}
        
