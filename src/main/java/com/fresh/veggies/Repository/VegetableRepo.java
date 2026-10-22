package com.fresh.veggies.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fresh.veggies.Entity.Vegetable;

public interface VegetableRepo extends JpaRepository<Vegetable, Long> {
    public List<Vegetable> findByCategoryId(Long categoryId);
}
