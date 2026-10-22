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

import com.fresh.veggies.Entity.CrateEntry;
import com.fresh.veggies.Services.CrateEntryService;

@RestController
@RequestMapping("/api/crate-entry")
public class CrateEntryController {

    private final CrateEntryService crateEntryService;

    public CrateEntryController(CrateEntryService crateEntryService) {
        this.crateEntryService = crateEntryService;
    }

    @PostMapping("/add")
    public ResponseEntity<CrateEntry> addCrateEntry(@RequestBody CrateEntry crateEntry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crateEntryService.addCrateEntry(crateEntry));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CrateEntry>> getAllCrateEntries() {
        return ResponseEntity.ok(crateEntryService.getAllCrateEntries());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CrateEntry> getCrateEntryById(@PathVariable Long id) {
        return ResponseEntity.ok(crateEntryService.getCrateEntryById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CrateEntry>> getCrateEntriesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(crateEntryService.getCrateEntriesByCustomerId(customerId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CrateEntry> updateCrateEntry(@PathVariable Long id, @RequestBody CrateEntry crateEntry) {
        return ResponseEntity.ok(crateEntryService.updateCrateEntry(id, crateEntry));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCrateEntry(@PathVariable Long id) {
        crateEntryService.deleteCrateEntry(id);
        return ResponseEntity.ok("Crate entry deleted successfully");
    }
}
