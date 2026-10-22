package com.fresh.veggies.Repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fresh.veggies.Entity.Purchase;

public interface PurchaseRepo extends JpaRepository<Purchase, Long> {
    public List<Purchase> findBySupplierId(Long supplierId);
    List<Purchase> findByEntryDate(LocalDate entryDate);
    List<Purchase> findTop3BySupplierIdOrderByEntryDateDesc(Long supplierId);


}
