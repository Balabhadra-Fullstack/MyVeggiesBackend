package com.fresh.veggies.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.DTO.SupplierMoneyCalculationRes;
import com.fresh.veggies.Entity.Supplier;
import com.fresh.veggies.Services.SupplierService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Supplier>> addSupplier(@Valid @RequestBody Supplier supplier) {

        Supplier addedSupplier = supplierService.addSupplier(supplier);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Supplier added successfully", addedSupplier));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {

        return ResponseEntity.ok(
                supplierService.getAllSuppliers()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Supplier> getSupplierById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Supplier>> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody Supplier supplier) {

        Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);

        return ResponseEntity.ok(new ApiResponse<>("Supplier updated successfully", updatedSupplier));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSupplier(
            @PathVariable Long id) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.ok(new ApiResponse<>("Supplier deleted successfully", "Supplier with id " + id + " has been deleted"));
    }
    @GetMapping("/search")
    public ResponseEntity<List<SupplierMoneyCalculationRes>> search(@RequestParam("name") String name){
        List<SupplierMoneyCalculationRes> response= supplierService.searchSupplier(name);
      return ResponseEntity.ok(response);
    }
    @GetMapping("/searchSupplier")
    public ResponseEntity<List<Supplier>>searchSupplier(@RequestParam("name")String name){
        List<Supplier>response=supplierService.search(name);
        return ResponseEntity.ok(response);
    }
}