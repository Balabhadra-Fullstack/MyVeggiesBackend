package com.fresh.veggies.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fresh.veggies.Repository.OwnerRepo;

@Service
public class OwnerUserDetailsService implements UserDetailsService {
    private final OwnerRepo ownerRepo;

    public OwnerUserDetailsService(OwnerRepo ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return ownerRepo.findByEmailIgnoreCase(email)
                .map(OwnerUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Owner not found"));
    }

}
