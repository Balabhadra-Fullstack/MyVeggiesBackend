package com.fresh.veggies.Controller;

import java.util.List;
import java.time.LocalDate;

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
import com.fresh.veggies.DTO.CrateResponseDTO;
import com.fresh.veggies.DTO.SaleRequest;
import com.fresh.veggies.DTO.SaleResponseDTO;
import com.fresh.veggies.Entity.Sale;
import com.fresh.veggies.Services.SaleService;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addSale(@RequestBody SaleRequest request) {
        String message = saleService.addSale(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(message, message));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Sale>> getSalesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(saleService.getSalesByCustomerId(customerId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updateSale(@PathVariable Long id, @RequestBody SaleRequest request) {
        saleService.updateSale(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Sale updated successfully", "Sale with id " + id + " has been updated"));
    }

    @GetMapping("/getbydate")
    public ResponseEntity<List<Sale>> getByDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(saleService.getByDate(date));
    }

    @GetMapping("/last3Sale/{customerId}")
    public ResponseEntity<List<SaleResponseDTO>> last3Sale(@PathVariable Long customerId) {
        return ResponseEntity.ok(saleService.last3Sale(customerId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.ok(new ApiResponse<>("Sale deleted successfully", "Sale with id " + id + " has been deleted"));
    }
    @GetMapping("/crate-details/{customerId}")
     public ResponseEntity<List<CrateResponseDTO>> getCrateDetailsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(saleService.getCrateDetailsByCustomerId(customerId));
    }
    
}
