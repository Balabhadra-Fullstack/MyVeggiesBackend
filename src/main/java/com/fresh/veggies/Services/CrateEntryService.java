package com.fresh.veggies.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresh.veggies.Entity.CrateEntry;
import com.fresh.veggies.Entity.Customer;
import com.fresh.veggies.Exception.CustomerNotFoundException;
import com.fresh.veggies.Repository.CrateEntryRepo;
import com.fresh.veggies.Repository.CustomerRepo;

@Service
public class CrateEntryService {

    private final CrateEntryRepo crateEntryRepository;
    private final CustomerRepo customerRepository;

    public CrateEntryService(CrateEntryRepo crateEntryRepository, CustomerRepo customerRepository) {
        this.crateEntryRepository = crateEntryRepository;
        this.customerRepository = customerRepository;
    }

    public CrateEntry addCrateEntry(CrateEntry crateEntry) {
        Customer customer = customerRepository.findById(crateEntry.getCustomer().getId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + crateEntry.getCustomer().getId()));

        crateEntry.setCustomer(customer);
        return crateEntryRepository.save(crateEntry);
    }

    public List<CrateEntry> getAllCrateEntries() {
        return crateEntryRepository.findAll();
    }

    public CrateEntry getCrateEntryById(Long id) {
        return crateEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crate entry not found with id: " + id));
    }

    public List<CrateEntry> getCrateEntriesByCustomerId(Long customerId) {
        return crateEntryRepository.findByCustomerId(customerId);
    }

    @Transactional
    public CrateEntry updateCrateEntry(Long id, CrateEntry crateEntry) {
        CrateEntry existing = getCrateEntryById(id);

        Customer customer = customerRepository.findById(crateEntry.getCustomer().getId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + crateEntry.getCustomer().getId()));

        existing.setCustomer(customer);
        existing.setQuantity(crateEntry.getQuantity());
        existing.setEntryDate(crateEntry.getEntryDate());

        return crateEntryRepository.save(existing);
    }

    public void deleteCrateEntry(Long id) {
        CrateEntry existing = getCrateEntryById(id);
        crateEntryRepository.delete(existing);
    }
}
