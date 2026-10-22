package com.fresh.veggies.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fresh.veggies.DTO.SupplierMoneyCalculationRes;
import com.fresh.veggies.Entity.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {
 

  @Query("""
    SELECT new com.fresh.veggies.DTO.SupplierMoneyCalculationRes(
        s.name,
        s.id,
        s.mobileNumber,
        COALESCE(
            (SELECT SUM(p.netAmount)
             FROM Purchase p
             WHERE p.supplier.id = s.id),
            0
        ),

        COALESCE(
            (SELECT SUM(sp.paymentAmount)
             FROM SupplierPayment sp
             WHERE sp.supplier.id = s.id),
            0
        ),

        COALESCE(
            (SELECT SUM(p.netAmount)
             FROM Purchase p
             WHERE p.supplier.id = s.id),
            0
        )
        -
        COALESCE(
            (SELECT SUM(sp.paymentAmount)
             FROM SupplierPayment sp
             WHERE sp.supplier.id = s.id),
            0
        )
    )
    FROM Supplier s
    WHERE LOWER(s.name) LIKE LOWER(CONCAT(:name, '%'))
""")
    List<SupplierMoneyCalculationRes> searchSupplier(
            @Param("name") String name
    );

boolean existsBynameIgnoreCase(String name);

   @Query("""
    SELECT s
    FROM Supplier s
    WHERE LOWER(s.name) LIKE LOWER(CONCAT(:name, '%'))
""")
List<Supplier> search(@Param("name") String name);
}

