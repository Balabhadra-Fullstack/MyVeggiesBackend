package com.fresh.veggies.Services;

import com.fresh.veggies.Entity.VegCategory;
import com.fresh.veggies.Repository.VegCategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VegCategoryService {

    private final VegCategoryRepo vegCategoryRepository;

    public VegCategoryService(VegCategoryRepo vegCategoryRepository) {
        this.vegCategoryRepository = vegCategoryRepository;
    }

    public VegCategory addVegCategory(VegCategory vegCategory) {
        return vegCategoryRepository.save(vegCategory);
    }

    public List<VegCategory> getAllVegCategories() {
        return vegCategoryRepository.findAll();
    }

    public VegCategory getVegCategoryById(Long id) {
        return vegCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VegCategory not found with id: " + id));
    }

    public VegCategory updateVegCategory(Long id, VegCategory vegCategory) {
        VegCategory existingCategory = getVegCategoryById(id);

        existingCategory.setName(vegCategory.getName());

        return vegCategoryRepository.save(existingCategory);
    }

    public void deleteVegCategory(Long id) {
        VegCategory existingCategory = getVegCategoryById(id);
        vegCategoryRepository.delete(existingCategory);
    }
}
