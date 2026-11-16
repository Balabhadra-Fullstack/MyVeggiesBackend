package com.fresh.veggies.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.SupplierPayment;

public interface SupplierPaymentRepo extends JpaRepository<SupplierPayment, Long> {
    public List<SupplierPayment> findBySupplierId(Long supplierId);
    List<SupplierPayment> findTop3BySupplierIdOrderByPaymentDateTimeDesc(Long supplierId);

    Page<SupplierPayment> findBySupplierIdAndPaymentDateTimeBetweenOrderByPaymentDateTimeDesc(
            Long supplierId, LocalDate startDate, LocalDate endDate, org.springframework.data.domain.Pageable pageable);

}
