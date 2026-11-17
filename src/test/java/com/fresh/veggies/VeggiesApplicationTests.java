package com.fresh.veggies;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fresh.veggies.Entity.Owner;
import com.fresh.veggies.Repository.OwnerRepo;
import com.fresh.veggies.Security.JwtUtil;

@SpringBootTest(properties = {
		"app.jwt.secret=local-test-secret-that-is-at-least-32-characters",
		"spring.datasource.url=jdbc:h2:mem:veggies_test;DB_CLOSE_DELAY=-1"
})
class VeggiesApplicationTests {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private OwnerRepo ownerRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Test
	void contextLoads() {
	}

	@Test
	void ownerLoginIssuesValidToken() {
		Owner owner = new Owner();
		owner.setName("Test owner");
		owner.setEmail("owner@example.com");
		owner.setPassword(passwordEncoder.encode("owner-password"));
		owner = ownerRepo.save(owner);

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(owner.getEmail(), "owner-password"));
			String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

			assertTrue(jwtUtil.isTokenValid(token, (UserDetails) authentication.getPrincipal()));
		} finally {
			ownerRepo.deleteById(owner.getId());
		}
	}
}
