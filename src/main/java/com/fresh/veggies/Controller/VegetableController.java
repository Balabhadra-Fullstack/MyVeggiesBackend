package com.fresh.veggies.Controller;

import com.fresh.veggies.DTO.addVeggies;
import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.Entity.Vegetable;
import com.fresh.veggies.Services.VegetableService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/vegetables")
public class VegetableController {

    private final VegetableService vegetableService;

    public VegetableController(VegetableService vegetableService) {
        this.vegetableService = vegetableService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Vegetable>> addVegetable(
            @Validated({Default.class, addVeggies.FullRequest.class}) @RequestBody addVeggies vegetable) {
        Vegetable addedVegetable = vegetableService.addVegetable(vegetable);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Vegetable added successfully", addedVegetable));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Vegetable>> getAllVegetables() {
        return ResponseEntity.ok(vegetableService.getAllVegetables());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Vegetable> getVegetableById(@PathVariable Long id) {
        return ResponseEntity.ok(vegetableService.getVegetableById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Vegetable>> updateVegetable(
            @PathVariable Long id,
            @Validated({Default.class, addVeggies.FullRequest.class}) @RequestBody addVeggies vegetable) {
        Vegetable updatedVegetable = vegetableService.updateVegetable(id, vegetable);
        return ResponseEntity.ok(new ApiResponse<>("Vegetable updated successfully", updatedVegetable));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Vegetable>> patchVegetable(
            @PathVariable Long id, @Valid @RequestBody addVeggies vegetable) {
        Vegetable patchedVegetable = vegetableService.patchVegetable(id, vegetable);
        return ResponseEntity.ok(new ApiResponse<>("Vegetable partially updated successfully", patchedVegetable));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVegetable(@PathVariable Long id) {
        vegetableService.deleteVegetable(id);
        return ResponseEntity.ok(new ApiResponse<>("Vegetable deleted successfully", "Vegetable with id " + id + " has been deleted"));
    }
    @GetMapping("/getByid/{id}")
    public ResponseEntity<List<Vegetable>> getallbyid(@PathVariable Long id){
       List<Vegetable> list= vegetableService.getVegetablesByCategoryId(id);
           return ResponseEntity.ok(list);
    }
}
