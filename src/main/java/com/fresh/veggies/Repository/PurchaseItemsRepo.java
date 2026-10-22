package com.fresh.veggies.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.PurchaseItems;

public interface PurchaseItemsRepo extends JpaRepository<PurchaseItems, Long> {
}
