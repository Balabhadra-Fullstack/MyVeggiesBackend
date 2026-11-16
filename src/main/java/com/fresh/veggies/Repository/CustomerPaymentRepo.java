package com.fresh.veggies.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.CustomerPayment;

public interface CustomerPaymentRepo extends JpaRepository<CustomerPayment, Long> {
    List<CustomerPayment> findByCustomerId(Long customerId);
    List<CustomerPayment> findTop3ByCustomerIdOrderByPaymentDateTimeDesc(Long customerId);
    Page<CustomerPayment> findByCustomerIdAndPaymentDateTimeBetweenOrderByPaymentDateTimeDesc(
            Long customerId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
