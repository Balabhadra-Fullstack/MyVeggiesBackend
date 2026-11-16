package com.fresh.veggies.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fresh.veggies.DTO.CrateResponseDTO;
import com.fresh.veggies.Entity.Sale;

public interface SaleRepo extends JpaRepository<Sale, Long> {
    List<Sale> findByCustomerId(Long customerId);
    List<Sale> findTop3ByCustomerIdOrderBySaleDateDesc(Long customerId);
   public   List <Sale> findTop30ByCustomerIdOrderBySaleDateDesc(Long customerId);
    List<Sale> findBySaleDate(java.time.LocalDate saleDate);
    Page<Sale> findByCustomerIdAndSaleDateBetweenOrderBySaleDateDesc(Long customerId,LocalDate startDate,LocalDate endDate,Pageable pageable);
  @Query("""
    SELECT new com.fresh.veggies.DTO.CrateResponseDTO(
        c.name,

        COALESCE((
            SELECT SUM(ce.quantity)
            FROM CrateEntry ce
            WHERE ce.customer.id = :customerId
        ), 0),

        COALESCE((
            SELECT SUM(cr.quantity)
            FROM CrateReturnEntry cr
            WHERE cr.customer.id = :customerId
        ), 0),

        COALESCE((
            SELECT SUM(ce.quantity)
            FROM CrateEntry ce
            WHERE ce.customer.id = :customerId
        ), 0)
        -
        COALESCE((
            SELECT SUM(cr.quantity)
            FROM CrateReturnEntry cr
            WHERE cr.customer.id = :customerId
        ), 0)
    )
    FROM Customer c
    WHERE c.id = :customerId
""")
List<CrateResponseDTO> findCrateDetailsByCustomerId(Long customerId);
List<Sale> findByCustomerIdAndSaleDateBetweenOrderBySaleDateDesc(Long customerId,LocalDate startDate,LocalDate endDate);
}
