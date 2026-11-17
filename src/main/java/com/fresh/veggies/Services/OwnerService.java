package com.fresh.veggies.Services;

import com.fresh.veggies.Entity.Owner;
import com.fresh.veggies.Repository.OwnerRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepo ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerService(OwnerRepo ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Owner addOwner(Owner owner) {
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
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
        existingOwner.setPassword(passwordEncoder.encode(owner.getPassword()));
        existingOwner.setPhoneNumber(owner.getPhoneNumber());
        existingOwner.setAddress(owner.getAddress());

        return ownerRepository.save(existingOwner);
    }

    public void deleteOwner(Long id) {
        Owner existingOwner = getOwnerById(id);
        ownerRepository.delete(existingOwner);
    }
}
