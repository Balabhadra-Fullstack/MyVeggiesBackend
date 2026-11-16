package com.fresh.veggies.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fresh.veggies.DTO.CustomerPaymentResponseDto;
import com.fresh.veggies.DTO.CustomerpaymentReq;
import com.fresh.veggies.Entity.Customer;
import com.fresh.veggies.Entity.CustomerPayment;
import com.fresh.veggies.Exception.CustomerNotFoundException;
import com.fresh.veggies.Repository.CustomerPaymentRepo;
import com.fresh.veggies.Repository.CustomerRepo;

import jakarta.transaction.Transactional;

@Service
public class CustomerPaymentService {

	private final CustomerPaymentRepo customerPaymentRepository;
	private final CustomerRepo customerRepository;

	public CustomerPaymentService(CustomerPaymentRepo customerPaymentRepository, CustomerRepo customerRepository) {
		this.customerPaymentRepository = customerPaymentRepository;
		this.customerRepository = customerRepository;
	}

	@Transactional
	public CustomerPayment addCustomerPayment(CustomerpaymentReq customerPaymentRequest) {
		Customer customer = customerRepository.findById(customerPaymentRequest.getId())
				.orElseThrow(() -> new CustomerNotFoundException(
						"Customer not found with id: " + customerPaymentRequest.getId()));

		CustomerPayment payment = new CustomerPayment();
		payment.setCustomer(customer);
		payment.setPaymentAmount(customerPaymentRequest.getPaymentamount());
		payment.setPaymentMethod(customerPaymentRequest.getPaymentmethod());
		payment.setPaymentDateTime(customerPaymentRequest.getPaymenttime());
		return customerPaymentRepository.save(payment);
	}

	public List<CustomerPayment> getAllCustomerPayments() {
		return customerPaymentRepository.findAll();
	}

	public CustomerPayment getCustomerPaymentById(Long id) {
		return customerPaymentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer payment not found with id: " + id));
	}

	public CustomerPayment updateCustomerPayment(Long id, CustomerPayment customerPayment) {
		CustomerPayment existingPayment = getCustomerPaymentById(id);
		existingPayment.setCustomer(customerPayment.getCustomer());
		existingPayment.setPaymentAmount(customerPayment.getPaymentAmount());
		existingPayment.setPaymentMethod(customerPayment.getPaymentMethod());
		existingPayment.setPaymentDateTime(customerPayment.getPaymentDateTime());
		return customerPaymentRepository.save(existingPayment);
	}

	public void deleteCustomerPayment(Long id) {
		customerPaymentRepository.delete(getCustomerPaymentById(id));
	}

	public List<CustomerPaymentResponseDto> last3PaymentByCustomerId(Long customerId) {
		return customerPaymentRepository.findTop3ByCustomerIdOrderByPaymentDateTimeDesc(customerId)
				.stream().map(this::convertToDTO).toList();
	}

	public Page<CustomerPaymentResponseDto> getCustomerPaymentsByCustomerId(
			Long customerId, Pageable pageable, LocalDate startDate, LocalDate endDate) {
		return customerPaymentRepository
				.findByCustomerIdAndPaymentDateTimeBetweenOrderByPaymentDateTimeDesc(
						customerId, startDate, endDate, pageable)
				.map(this::convertToDTO);
	}

	public CustomerPaymentResponseDto convertToDTO(CustomerPayment payment) {
		CustomerPaymentResponseDto dto = new CustomerPaymentResponseDto();
		dto.setPaymentId(payment.getId());
		dto.setCustomerId(payment.getCustomer().getId());
		dto.setCustomerName(payment.getCustomer().getName());
		dto.setAmount(payment.getPaymentAmount());
		dto.setMethod(payment.getPaymentMethod());
		dto.setDateTime(payment.getPaymentDateTime());
		return dto;
	}
	public CustomerPaymentResponseDto getByPaymentId(Long paymentId) {
		CustomerPayment payment = customerPaymentRepository.findById(paymentId)
				.orElseThrow(() -> new RuntimeException("Customer payment not found with id: " + paymentId));
		return convertToDTO(payment);
	}
}
