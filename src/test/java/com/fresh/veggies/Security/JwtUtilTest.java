package com.fresh.veggies.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fresh.veggies.Entity.Owner;

import io.jsonwebtoken.JwtException;

class JwtUtilTest {
    private final JwtUtil jwtUtil = new JwtUtil(
            "local-test-secret-that-is-at-least-32-characters",
            60_000);

    @Test
    void generatedTokenIdentifiesOwnerAndValidatesAgainstEmail() {
        Owner owner = new Owner();
        owner.setEmail("owner@example.com");
        owner.setPassword("encoded-password");
        OwnerUserDetails userDetails = new OwnerUserDetails(owner);
        String token = jwtUtil.generateToken(userDetails);

        assertEquals("owner@example.com", jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.isTokenValid(token, userDetails));

        Owner otherOwner = new Owner();
        otherOwner.setEmail("other@example.com");
        assertFalse(jwtUtil.isTokenValid(token, new OwnerUserDetails(otherOwner)));
    }

    @Test
    void tamperedTokenIsRejected() {
        Owner owner = new Owner();
        owner.setEmail("owner@example.com");
        String token = jwtUtil.generateToken(new OwnerUserDetails(owner));
        int payloadCharacterIndex = token.indexOf('.') + 1;
        char payloadCharacter = token.charAt(payloadCharacterIndex);
        String tamperedToken = token.substring(0, payloadCharacterIndex)
                + (payloadCharacter == 'a' ? 'b' : 'a')
                + token.substring(payloadCharacterIndex + 1);

        assertThrows(JwtException.class, () -> jwtUtil.extractUsername(tamperedToken));
    }
}
