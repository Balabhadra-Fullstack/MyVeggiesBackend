package com.fresh.veggies.Controller;

import java.util.List;
import java.time.LocalDate;

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
import com.fresh.veggies.DTO.CrateResponseDTO;
import com.fresh.veggies.DTO.SaleRequest;
import com.fresh.veggies.DTO.SaleResponseDTO;
import com.fresh.veggies.DTO.SaleResponseWithCrate;
import com.fresh.veggies.Entity.Sale;
import com.fresh.veggies.Services.SaleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@Validated
@RequestMapping("/api/sale")
@SecurityRequirement(name = "bearerAuth")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addSale(@Valid @RequestBody SaleRequest request) {
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
    public ResponseEntity<ApiResponse<String>> updateSale(@PathVariable Long id, @Valid @RequestBody SaleRequest request) {
        saleService.updateSale(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Sale updated successfully", "Sale with id " + id + " has been updated"));
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
    @GetMapping("/last30/{customerId}")
   public ResponseEntity<List<SaleResponseWithCrate>> getTop30sale(@PathVariable Long customerId){
     return  ResponseEntity.ok(saleService.get30(customerId));
   }
    @GetMapping("/getsaleBetweenDate/{id}/date-range")
    public ResponseEntity<Page<SaleResponseDTO>> getSaleBetweenDate(@PathVariable Long id,@RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate,
        @Min(0) @RequestParam(defaultValue = "0") int page,
        @Positive @RequestParam(defaultValue = "30") int size){
           Pageable data=PageRequest.of(page, size);
           Page<SaleResponseDTO> sales=saleService.getdata(id, startDate, endDate, data);
          return ResponseEntity.ok(sales);
    }

    @GetMapping("/getsalewithcrate/{id}")
    public ResponseEntity<SaleResponseWithCrate> getSaleByIdWithCrate(@PathVariable Long id) {
        SaleResponseWithCrate sale = saleService.getSaleByID(id);
        return ResponseEntity.ok(sale);
    }

}
