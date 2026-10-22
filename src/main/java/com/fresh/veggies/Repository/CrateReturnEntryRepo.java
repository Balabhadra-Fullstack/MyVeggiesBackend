package com.fresh.veggies.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.CrateReturnEntry;

public interface CrateReturnEntryRepo extends JpaRepository<CrateReturnEntry, Long> {
    List<CrateReturnEntry> findByCustomerId(Long customerId);
}
