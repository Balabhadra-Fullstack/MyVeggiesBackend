package com.fresh.veggies.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fresh.veggies.DTO.CustomerMoneyCalculationRes;
import com.fresh.veggies.Entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
	@Query("""
			SELECT new com.fresh.veggies.DTO.CustomerMoneyCalculationRes(
				c.name,
				c.id,
				c.mobileNumber,
				COALESCE(
					(SELECT SUM(s.Totalsaleamount)
					 FROM Sale s
					 WHERE s.customer.id = c.id),
					0
				),
				COALESCE(
					(SELECT SUM(cp.paymentAmount)
					 FROM CustomerPayment cp
					 WHERE cp.customer.id = c.id),
					0
				),
				COALESCE(
					(SELECT SUM(s.Totalsaleamount)
					 FROM Sale s
					 WHERE s.customer.id = c.id),
					0
				)
				-
				COALESCE(
					(SELECT SUM(cp.paymentAmount)
					 FROM CustomerPayment cp
					 WHERE cp.customer.id = c.id),
					0
				)
			)
			FROM Customer c
			WHERE LOWER(c.name) LIKE LOWER(CONCAT(:name, '%'))
			""")
	List<CustomerMoneyCalculationRes> searchCustomer(@Param("name") String name);

	@Query("""
			SELECT c
			FROM Customer c
			WHERE LOWER(c.name) LIKE LOWER(CONCAT(:name, '%'))
			""")
	List<Customer> search(@Param("name") String name);

	boolean existsBynameIgnoreCase(String name);
}
