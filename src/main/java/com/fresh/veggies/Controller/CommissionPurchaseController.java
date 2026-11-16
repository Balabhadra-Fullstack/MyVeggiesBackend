package com.fresh.veggies.Controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.validation.annotation.Validated;

import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.DTO.CommisionRequest;
import com.fresh.veggies.DTO.CommissionResponse;
import com.fresh.veggies.Entity.CommissionPurchase;
import com.fresh.veggies.Services.CommissionPurchaseService;

@RestController
@Validated
@RequestMapping("/api/commission-purchases")
public class CommissionPurchaseController {

    private final CommissionPurchaseService commissionPurchaseService;

    public CommissionPurchaseController(CommissionPurchaseService commissionPurchaseService) {
        this.commissionPurchaseService = commissionPurchaseService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CommissionPurchase>> addCommissionPurchase(@Valid @RequestBody CommisionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Commission purchase created successfully",
                        commissionPurchaseService.addCommissionPurchase(request)));
    }

    @GetMapping("/getall")
    public ResponseEntity<ApiResponse<List<CommissionPurchase>>> getAllCommissionPurchases() {
        return ResponseEntity.ok(new ApiResponse<>("Commission purchases retrieved successfully",
                commissionPurchaseService.getAllCommissionPurchases()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CommissionPurchase>> getCommissionPurchaseById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>("Commission purchase retrieved successfully",
                commissionPurchaseService.getCommissionPurchaseById(id)));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<CommissionPurchase>>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(new ApiResponse<>("Commission purchases retrieved successfully",
                commissionPurchaseService.getCommissionPurchasesBySupplierId(supplierId)));
    }

    @GetMapping("/getbydate")
    public ResponseEntity<ApiResponse<List<CommissionPurchase>>> getByDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(new ApiResponse<>("Commission purchases retrieved successfully",
                commissionPurchaseService.getCommissionPurchasesByDate(date)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommissionPurchase> updateCommissionPurchase(
            @PathVariable Long id, @Valid @RequestBody CommisionRequest request) {
        return ResponseEntity.ok(commissionPurchaseService.updateCommissionPurchase(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCommissionPurchase(@PathVariable Long id) {
        commissionPurchaseService.deleteCommissionPurchase(id);
        return ResponseEntity.ok(new ApiResponse<>("Commission purchase deleted successfully",
                "Commission purchase with id " + id + " has been deleted"));
    }
    @GetMapping("/top3/{supplierId}")
    public ResponseEntity<List<CommissionResponse>> getTop3CommissionPurchasesBySupplier(@PathVariable Long supplierId) {
        List<CommissionResponse> top3Purchases = commissionPurchaseService.getlast3CommissionPurchasesBySupplierId(supplierId);
        return ResponseEntity.ok(top3Purchases);
    }
    @GetMapping("/top30/{supplierId}")
    public ResponseEntity<List<CommissionResponse>> getTop30CommissionPurchasesBySupplier(@PathVariable Long supplierId) {
        List<CommissionResponse> top30Purchases = commissionPurchaseService.getlast30CommissionPurchasesBySupplierId(supplierId);
        return ResponseEntity.ok(top30Purchases);
    }

    @GetMapping("/getPaginationData/{id}/date-range")
    public ResponseEntity<Page<CommissionResponse>> getCommissionPurchasesBetweenDates(
            @PathVariable Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Positive @RequestParam(defaultValue = "30") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(commissionPurchaseService.getdata(id, startDate, endDate, pageable));
    }
}