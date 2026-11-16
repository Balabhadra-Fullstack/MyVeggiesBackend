package com.fresh.veggies.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.CommissionPurchase;

public interface CommissionPurchaseRepo extends JpaRepository<CommissionPurchase, Long> {
    List<CommissionPurchase> findBySupplierId(Long supplierId);

    List<CommissionPurchase> findByPurchaseDate(LocalDate purchaseDate);
    List<CommissionPurchase> findTop3BySupplierIdOrderByPurchaseDateDesc(Long supplierId);
    List<CommissionPurchase> findTop30BySupplierIdOrderByPurchaseDateDesc(
        Long supplierId
    );
    Page<CommissionPurchase> findBySupplierIdAndPurchaseDateBetweenOrderByPurchaseDateDesc(Long supplierId,LocalDate startDate,LocalDate endDate,Pageable pageable);
     List<CommissionPurchase> findBySupplierIdAndPurchaseDateBetweenOrderByPurchaseDateDesc(Long supplierId,LocalDate startDate,LocalDate endDate);
}