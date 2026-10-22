package com.fresh.veggies.Controller;

import com.fresh.veggies.DTO.PurchaseRequestDTO;
import com.fresh.veggies.DTO.PurchaseResponseDTO;
import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.Entity.Purchase;
import com.fresh.veggies.Services.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
       String msg= purchaseService.addPurchase(purchaseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(msg, msg));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getPurchaseById(id));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<Purchase>> getPurchasesBySupplierId(@PathVariable Long supplierId) {
        return ResponseEntity.ok(purchaseService.getPurchasesBySupplierId(supplierId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<String>> updatePurchase(@PathVariable Long id, @RequestBody PurchaseRequestDTO purchase) {
        purchaseService.updatePurchase(id, purchase);
        return ResponseEntity.ok(new ApiResponse<>("Purchase updated successfully", "Purchase with id " + id + " has been updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok(new ApiResponse<>("Purchase deleted successfully", "Purchase with id " + id + " has been deleted"));
    }
    @GetMapping("/getbydate")
    public ResponseEntity<List<Purchase>> getByDate(@RequestParam LocalDate date){
        return ResponseEntity.ok(purchaseService.getByDate(date));
    }
    @GetMapping("/last3Purchase/{id}")
    public ResponseEntity<List<PurchaseResponseDTO>> last3Record(@PathVariable Long id){
        List<PurchaseResponseDTO> items=purchaseService.last3Purchase(id);
        return ResponseEntity.ok(items);
    }
    }
    

