package com.fresh.veggies.Controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.DTO.CustomerMoneyCalculationRes;
import com.fresh.veggies.Entity.Customer;
import com.fresh.veggies.Services.CustomerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/customers")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Customer>> addCustomer(@Valid @RequestBody Customer customer) {
        Customer addedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Customer added successfully", addedCustomer));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(new ApiResponse<>("Customer updated successfully", updatedCustomer));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(new ApiResponse<>(
                "Customer deleted successfully",
                "Customer with id " + id + " has been deleted"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerMoneyCalculationRes>> search(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(customerService.searchCustomer(name));
    }

    @GetMapping("/searchCustomer")
    public ResponseEntity<List<Customer>> searchCustomer(
            @RequestParam("name") String name) {
        return ResponseEntity.ok(customerService.search(name));
    }
}