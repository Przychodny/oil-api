package com.example.oil_api.repositories;

import com.example.oil_api.models.entities.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findWithLockingById(int id);

    Optional<User> findByUsername(String username);
}
