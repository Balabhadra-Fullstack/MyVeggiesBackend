package com.fresh.veggies.Repository;

import java.util.Optional;

import com.fresh.veggies.Entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepo extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmailIgnoreCase(String email);
}
