package com.fresh.veggies.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.VegCategory;

public interface VegCategoryRepo extends JpaRepository<VegCategory, Long> {
}
