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
import org.springframework.web.bind.annotation.RestController;

import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.DTO.crateReturnRequestDTO;
import com.fresh.veggies.Entity.CrateReturnEntry;
import com.fresh.veggies.Services.CrateReturnEntryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/crate-return")
@SecurityRequirement(name = "bearerAuth")
public class CrateReturnEntryController {

    private final CrateReturnEntryService crateReturnEntryService;

    public CrateReturnEntryController(CrateReturnEntryService crateReturnEntryService) {
        this.crateReturnEntryService = crateReturnEntryService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CrateReturnEntry>> addCrateReturnEntry(@Valid @RequestBody crateReturnRequestDTO crateReturnEntry) {
        CrateReturnEntry savedEntry = crateReturnEntryService.addCrateReturnEntry(crateReturnEntry);
                           if(savedEntry==null){
                               ApiResponse<CrateReturnEntry> response=new ApiResponse<>(); 
                               response.setSuccess(false);
                               response.setMessage("Failed to add crate return entry");
                               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                           }
                        ApiResponse<CrateReturnEntry> response=new ApiResponse<>(); 
                        response.setSuccess(true);
                        response.setMessage("Crate return entry added successfully");
                        response.setData(savedEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CrateReturnEntry>> getAllCrateReturnEntries() {
        return ResponseEntity.ok(crateReturnEntryService.getAllCrateReturnEntries());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CrateReturnEntry> getCrateReturnEntryById(@PathVariable Long id) {
        return ResponseEntity.ok(crateReturnEntryService.getCrateReturnEntryById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CrateReturnEntry>> getCrateReturnEntriesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(crateReturnEntryService.getCrateReturnEntriesByCustomerId(customerId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CrateReturnEntry> updateCrateReturnEntry(@PathVariable Long id, @Valid @RequestBody CrateReturnEntry crateReturnEntry) {
        return ResponseEntity.ok(crateReturnEntryService.updateCrateReturnEntry(id, crateReturnEntry));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCrateReturnEntry(@PathVariable Long id) {
        crateReturnEntryService.deleteCrateReturnEntry(id);
        return ResponseEntity.ok("Crate return entry deleted successfully");
    }
}
