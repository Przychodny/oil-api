package com.example.oil_api.repositories;

import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Driver> findWithLockingById(int id);
}
