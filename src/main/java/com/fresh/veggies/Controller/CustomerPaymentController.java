package com.fresh.veggies.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.DTO.CustomerPaymentResponseDto;
import com.fresh.veggies.DTO.CustomerpaymentReq;
import com.fresh.veggies.Entity.CustomerPayment;
import com.fresh.veggies.Services.CustomerPaymentService;

@RestController
@RequestMapping("/api/customer-payments")
public class CustomerPaymentController {

    private final CustomerPaymentService customerPaymentService;

    public CustomerPaymentController(CustomerPaymentService customerPaymentService) {
        this.customerPaymentService = customerPaymentService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CustomerPayment>> addCustomerPayment(
            @RequestBody CustomerpaymentReq customerPayment) {
        CustomerPayment addedPayment = customerPaymentService.addCustomerPayment(customerPayment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Customer payment added successfully", addedPayment));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CustomerPayment>> getAllCustomerPayments() {
        return ResponseEntity.ok(customerPaymentService.getAllCustomerPayments());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerPayment> getCustomerPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(customerPaymentService.getCustomerPaymentById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CustomerPayment>> updateCustomerPayment(
            @PathVariable Long id,
            @RequestBody CustomerPayment customerPayment) {
        CustomerPayment updatedPayment = customerPaymentService.updateCustomerPayment(id, customerPayment);
        return ResponseEntity.ok(new ApiResponse<>("Customer payment updated successfully", updatedPayment));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomerPayment(@PathVariable Long id) {
        customerPaymentService.deleteCustomerPayment(id);
        return ResponseEntity.ok(new ApiResponse<>(
                "Customer payment deleted successfully",
                "Customer payment with id " + id + " has been deleted"));
    }

    @GetMapping("/last3/{customerId}")
    public ResponseEntity<List<CustomerPaymentResponseDto>> last3PaymentByCustomerId(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(customerPaymentService.last3PaymentByCustomerId(customerId));
    }
}