package com.fresh.veggies.Services;

import com.fresh.veggies.Entity.Owner;
import com.fresh.veggies.Repository.OwnerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepo ownerRepository;

    public OwnerService(OwnerRepo ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner addOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + id));
    }

    public Owner updateOwner(Long id, Owner owner) {
        Owner existingOwner = getOwnerById(id);

        existingOwner.setName(owner.getName());
        existingOwner.setEmail(owner.getEmail());
        existingOwner.setPassword(owner.getPassword());
        existingOwner.setPhoneNumber(owner.getPhoneNumber());
        existingOwner.setAddress(owner.getAddress());

        return ownerRepository.save(existingOwner);
    }

    public void deleteOwner(Long id) {
        Owner existingOwner = getOwnerById(id);
        ownerRepository.delete(existingOwner);
    }
}
