package com.fresh.veggies.Services;



import com.fresh.veggies.DTO.SupplierMoneyCalculationRes;
import com.fresh.veggies.Entity.Supplier;
import com.fresh.veggies.Exception.SupplierNotFoundException;
import com.fresh.veggies.Repository.SupplierRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepo supplierRepository;

    public SupplierService(SupplierRepo supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier addSupplier(Supplier supplier) {
        if (supplierRepository.existsBynameIgnoreCase(supplier.getName())) {
            throw new SupplierNotFoundException("Name Already Available in Record");
        }
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
    }

    public Supplier updateSupplier(Long id, Supplier supplier) {

        Supplier existingSupplier = getSupplierById(id);

        existingSupplier.setName(supplier.getName());
        existingSupplier.setMobileNumber(supplier.getMobileNumber());
        existingSupplier.setAddress(supplier.getAddress());

        return supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long id) {
        Supplier existingSupplier = getSupplierById(id);
        supplierRepository.delete(existingSupplier);
    }
  public List<SupplierMoneyCalculationRes> searchSupplier(String name) {
        List<SupplierMoneyCalculationRes> suppliers = supplierRepository.searchSupplier(name);
        return suppliers;
    }
    public List<Supplier>search(String name){
        return supplierRepository.search(name);
    }

}