package com.fresh.veggies.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class ownerRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;   
}
