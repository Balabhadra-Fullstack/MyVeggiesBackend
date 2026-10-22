package com.fresh.veggies.DTO;

import lombok.Getter;

@Getter
public class OwnerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public OwnerResponseDTO(Long id, String name, String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}