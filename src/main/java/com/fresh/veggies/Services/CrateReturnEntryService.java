package com.fresh.veggies.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fresh.veggies.DTO.crateReturnRequestDTO;
import com.fresh.veggies.Entity.CrateReturnEntry;
import com.fresh.veggies.Entity.Customer;
import com.fresh.veggies.Exception.CustomerNotFoundException;
import com.fresh.veggies.Repository.CrateReturnEntryRepo;
import com.fresh.veggies.Repository.CustomerRepo;

@Service
public class CrateReturnEntryService {

    private final CrateReturnEntryRepo crateReturnEntryRepository;
    private final CustomerRepo customerRepository;

    public CrateReturnEntryService(CrateReturnEntryRepo crateReturnEntryRepository, CustomerRepo customerRepository) {
        this.crateReturnEntryRepository = crateReturnEntryRepository;
        this.customerRepository = customerRepository;
    }

    public CrateReturnEntry addCrateReturnEntry(crateReturnRequestDTO ReturnEntry) {
        Customer customer = customerRepository.findById(ReturnEntry.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + ReturnEntry.getCustomerId()));

        CrateReturnEntry crateReturnEntry = new CrateReturnEntry();
        crateReturnEntry.setCustomer(customer);
        crateReturnEntry.setQuantity(ReturnEntry.getQuantity());
        crateReturnEntry.setReturnDate(ReturnEntry.getReturnDate());
        return crateReturnEntryRepository.save(crateReturnEntry);
    }

    public List<CrateReturnEntry> getAllCrateReturnEntries() {
        return crateReturnEntryRepository.findAll();
    }

    public CrateReturnEntry getCrateReturnEntryById(Long id) {
        return crateReturnEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crate return entry not found with id: " + id));
    }

    public List<CrateReturnEntry> getCrateReturnEntriesByCustomerId(Long customerId) {
        return crateReturnEntryRepository.findByCustomerId(customerId);
    }

    @Transactional
    public CrateReturnEntry updateCrateReturnEntry(Long id, CrateReturnEntry crateReturnEntry) {
        CrateReturnEntry existing = getCrateReturnEntryById(id);

        Customer customer = customerRepository.findById(crateReturnEntry.getCustomer().getId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + crateReturnEntry.getCustomer().getId()));

        existing.setCustomer(customer);
        existing.setQuantity(crateReturnEntry.getQuantity());
        existing.setReturnDate(crateReturnEntry.getReturnDate());

        return crateReturnEntryRepository.save(existing);
    }

    public void deleteCrateReturnEntry(Long id) {
        CrateReturnEntry existing = getCrateReturnEntryById(id);
        crateReturnEntryRepository.delete(existing);
    }
}
