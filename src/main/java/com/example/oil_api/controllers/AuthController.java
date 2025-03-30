package com.example.oil_api.controllers;

import com.example.oil_api.mappers.UserMapper;
import com.example.oil_api.models.RegisterRequest;
import com.example.oil_api.models.dto.UserDto;
import com.example.oil_api.models.entities.User;
import com.example.oil_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        UserDto userDto = UserMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
}
