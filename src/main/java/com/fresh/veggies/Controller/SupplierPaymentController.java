package com.fresh.veggies.Controller;

import com.fresh.veggies.DTO.SupplierpaymentReq;
import com.fresh.veggies.DTO.SupplierPaymentResponseDTO;
import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.Entity.SupplierPayment;
import com.fresh.veggies.Services.SupplierPaymentService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/supplier-payments")
public class SupplierPaymentController {

    private final SupplierPaymentService supplierPaymentService;

    public SupplierPaymentController(SupplierPaymentService supplierPaymentService) {
        this.supplierPaymentService = supplierPaymentService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<SupplierPayment>> addSupplierPayment(@Valid @RequestBody SupplierpaymentReq supplierPayment) {
        SupplierPayment addedPayment = supplierPaymentService.addSupplierPayment(supplierPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Supplier payment added successfully", addedPayment));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<SupplierPayment>> getAllSupplierPayments() {
        return ResponseEntity.ok(supplierPaymentService.getAllSupplierPayments());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SupplierPayment> getSupplierPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierPaymentService.getSupplierPaymentById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<SupplierPayment>> updateSupplierPayment(
            @PathVariable Long id,
            @Valid @RequestBody SupplierPayment supplierPayment) {
        SupplierPayment updatedPayment = supplierPaymentService.updateSupplierPayment(id, supplierPayment);
        return ResponseEntity.ok(new ApiResponse<>("Supplier payment updated successfully", updatedPayment));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSupplierPayment(@PathVariable Long id) {
        supplierPaymentService.deleteSupplierPayment(id);
        return ResponseEntity.ok(new ApiResponse<>("Supplier payment deleted successfully", "Supplier payment with id " + id + " has been deleted"));
    }

    @GetMapping("/last3/{supplierId}")
    public ResponseEntity<List<SupplierPaymentResponseDTO>> last3PaymentBySupplierId(@PathVariable Long supplierId) {
        List<SupplierPaymentResponseDTO> payments = supplierPaymentService.last3PaymentBySupplierId(supplierId);
        return ResponseEntity.ok(payments);
    }
    @GetMapping("/getbysupplierid/{supplierId}")
    public ResponseEntity<Page<SupplierPaymentResponseDTO>> getSupplierPaymentsBySupplierId(@PathVariable Long supplierId, @RequestParam LocalDate startDate,@RequestParam  LocalDate endDate, @Positive @RequestParam (defaultValue = "30") int size, @Min(0) @RequestParam (defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SupplierPaymentResponseDTO> paymentsPage = supplierPaymentService.getSupplierPaymentsBySupplierId(supplierId, pageable,startDate,endDate);
        return ResponseEntity.ok(paymentsPage);
    }
    @GetMapping("/getbypaymentid/{paymentId}")
   public ResponseEntity<SupplierPayment> getByPaymentId(@PathVariable Long paymentId){
        SupplierPayment payment=supplierPaymentService.getByPaymentId(paymentId);
        return ResponseEntity.ok(payment);
    }
}