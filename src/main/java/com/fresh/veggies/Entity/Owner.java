package com.fresh.veggies.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Owner name is required")
    private String name;
    @NotBlank(message = "Owner email is required")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Owner password is required")
    private String password;
    private String phoneNumber;
    private String address;

    public Owner(Long id, String name, String email, String password, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
