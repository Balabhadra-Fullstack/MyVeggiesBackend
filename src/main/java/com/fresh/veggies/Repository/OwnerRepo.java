package com.fresh.veggies.Repository;

import com.fresh.veggies.Entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepo extends JpaRepository<Owner, Long> {
}
