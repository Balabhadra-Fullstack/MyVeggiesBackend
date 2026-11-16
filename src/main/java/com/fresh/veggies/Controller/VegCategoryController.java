package com.fresh.veggies.Controller;

import com.fresh.veggies.Entity.VegCategory;
import com.fresh.veggies.DTO.ApiResponse;
import com.fresh.veggies.Services.VegCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vegcategories")
public class VegCategoryController {

    private final VegCategoryService vegCategoryService;

    public VegCategoryController(VegCategoryService vegCategoryService) {
        this.vegCategoryService = vegCategoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<VegCategory>> addVegCategory(@Valid @RequestBody VegCategory vegCategory) {
        VegCategory addedCategory = vegCategoryService.addVegCategory(vegCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("VegCategory added successfully", addedCategory));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<VegCategory>> getAllVegCategories() {
        return ResponseEntity.ok(vegCategoryService.getAllVegCategories());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<VegCategory> getVegCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(vegCategoryService.getVegCategoryById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<VegCategory>> updateVegCategory(@PathVariable Long id, @Valid @RequestBody VegCategory vegCategory) {
        VegCategory updatedCategory = vegCategoryService.updateVegCategory(id, vegCategory);
        return ResponseEntity.ok(new ApiResponse<>("VegCategory updated successfully", updatedCategory));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVegCategory(@PathVariable Long id) {
        vegCategoryService.deleteVegCategory(id);
        return ResponseEntity.ok(new ApiResponse<>("VegCategory deleted successfully", "VegCategory with id " + id + " has been deleted"));
    }
}
