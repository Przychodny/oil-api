package com.example.oil_api.repositories;

import com.example.oil_api.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
