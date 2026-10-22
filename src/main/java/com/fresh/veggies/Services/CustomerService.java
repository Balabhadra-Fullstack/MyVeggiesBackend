package com.fresh.veggies.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fresh.veggies.DTO.CustomerMoneyCalculationRes;
import com.fresh.veggies.Entity.Customer;
import com.fresh.veggies.Exception.CustomerNotFoundException;
import com.fresh.veggies.Repository.CustomerRepo;

@Service
public class CustomerService {
	private final CustomerRepo customerRepository;

	public CustomerService(CustomerRepo customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer addCustomer(Customer customer) {
		if (customerRepository.existsBynameIgnoreCase(customer.getName())) {
			throw new CustomerNotFoundException("Name already available in record");
		}
		return customerRepository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
	}

	public Customer updateCustomer(Long id, Customer customer) {
		Customer existingCustomer = getCustomerById(id);
		existingCustomer.setName(customer.getName());
		existingCustomer.setMobileNumber(customer.getMobileNumber());
		existingCustomer.setAddress(customer.getAddress());
		return customerRepository.save(existingCustomer);
	}

	public void deleteCustomer(Long id) {
		Customer existingCustomer = getCustomerById(id);
		customerRepository.delete(existingCustomer);
	}

	public List<CustomerMoneyCalculationRes> searchCustomer(String name) {
		return customerRepository.searchCustomer(name);
	}

	public List<Customer> search(String name) {
		return customerRepository.search(name);
	}
    
}
