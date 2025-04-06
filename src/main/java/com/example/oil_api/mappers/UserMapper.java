package com.example.oil_api.mappers;

import com.example.oil_api.common.Role;
import com.example.oil_api.models.RegisterRequest;
import com.example.oil_api.models.dto.UserDto;
import com.example.oil_api.models.entities.Admin;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.User;

import java.math.BigDecimal;

public class UserMapper { //konsekwencja - mapstruct. chyba, ze nie ma wyjscia, ale tutaj mozna mapstructem, jesli juz uzywasz mapstructa

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static User toUserEntity(RegisterRequest request, String encodedPassword) {
        if (request == null || request.getRole() == null) {
            throw new IllegalArgumentException("Request or role can't be null");
        }

        Role role = request.getRole();
        if (role == Role.DRIVER) { //ifologia sie zrobi, jesli bedzie wiecej rol.
            // Albo fabryka albo mapa<Role, BiFunction<RegisterRequest, String, User>
            return Driver.builder()//metoda
                    .username(request.getUsername())
                    .password(encodedPassword)
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .role(role)
                    .balance(request.getBalance() != null ? request.getBalance() : BigDecimal.ZERO)
                    .build();
        } else if (role == Role.ADMIN) {
            return Admin.builder()
                    .username(request.getUsername())
                    .password(encodedPassword)
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .role(role)
                    .build();
        } else {
            throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
