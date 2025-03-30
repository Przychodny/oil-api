package com.example.oil_api.models;

import com.example.oil_api.common.Role;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RegisterRequest {

    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
}
