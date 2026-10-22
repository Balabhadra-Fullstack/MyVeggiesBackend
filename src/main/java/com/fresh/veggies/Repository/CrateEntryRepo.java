package com.fresh.veggies.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.CrateEntry;

public interface CrateEntryRepo extends JpaRepository<CrateEntry, Long> {
    List<CrateEntry> findByCustomerId(Long customerId);
    Optional<CrateEntry> findBySaleId(Long saleId);
}
