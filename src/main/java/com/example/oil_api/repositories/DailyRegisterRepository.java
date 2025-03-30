package com.example.oil_api.repositories;

import com.example.oil_api.models.entities.DailyRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRegisterRepository extends JpaRepository<DailyRegister, Integer> {
}
