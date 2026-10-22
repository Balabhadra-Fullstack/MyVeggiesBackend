package com.fresh.veggies.Services;

import com.fresh.veggies.DTO.addVeggies;
import com.fresh.veggies.Entity.VegCategory;
import com.fresh.veggies.Entity.Vegetable;
import com.fresh.veggies.Repository.VegCategoryRepo;
import com.fresh.veggies.Repository.VegetableRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VegetableService {

    private final VegetableRepo vegetableRepository;
    private final VegCategoryRepo repo;
    public VegetableService(VegetableRepo vegetableRepository,VegCategoryRepo repo) {
        this.vegetableRepository = vegetableRepository;
        this.repo = repo;
    }

    public Vegetable addVegetable(addVeggies vegetable) {
                    VegCategory cat= repo.getReferenceById(vegetable.getCategory_id());
                    Vegetable veg= new Vegetable();
                    veg.setName(vegetable.getName());
                    veg.setOdianame(vegetable.getOdianame());
                    veg.setImage(vegetable.getImage());
                    veg.setCategory(cat);
        return vegetableRepository.save(veg);
    }

    public List<Vegetable> getAllVegetables() {
        return vegetableRepository.findAll();
    }

    public Vegetable getVegetableById(Long id) {
        return vegetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vegetable not found with id: " + id));
    }

    public Vegetable updateVegetable(Long id, addVeggies vegetable) {
        Vegetable existingVegetable = getVegetableById(id);
                    VegCategory  cat= repo.getReferenceById(vegetable.getCategory_id());
        existingVegetable.setName(vegetable.getName());
        existingVegetable.setImage(vegetable.getImage());
        existingVegetable.setCategory(cat);
          existingVegetable.setOdianame(vegetable.getOdianame());
        return vegetableRepository.save(existingVegetable);
    }

    public Vegetable patchVegetable(Long id, addVeggies vegetable) {
        Vegetable existingVegetable = getVegetableById(id);

        if (vegetable.getName() != null) {
            existingVegetable.setName(vegetable.getName());
        }
        if (vegetable.getOdianame() != null) {
            existingVegetable.setOdianame(vegetable.getOdianame());
        }
        if (vegetable.getImage() != null) {
            existingVegetable.setImage(vegetable.getImage());
        }
        if (vegetable.getCategory_id() != null) {
            existingVegetable.setCategory(repo.getReferenceById(vegetable.getCategory_id()));
        }

        return vegetableRepository.save(existingVegetable);
    }

    public void deleteVegetable(Long id) {
        Vegetable existingVegetable = getVegetableById(id);
        vegetableRepository.delete(existingVegetable);
    }
    public List<Vegetable> getVegetablesByCategoryId(Long categoryId) {
        return vegetableRepository.findByCategoryId(categoryId);
    }
}
