package com.example.oil_api.services;

import com.example.oil_api.mappers.UserMapper;
import com.example.oil_api.models.RegisterRequest;
import com.example.oil_api.models.entities.User;
import com.example.oil_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = UserMapper.toUserEntity(request, encodedPassword);
        return userRepository.save(user);
    }
}
