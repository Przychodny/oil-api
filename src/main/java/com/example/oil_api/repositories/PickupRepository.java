package com.example.oil_api.repositories;

import com.example.oil_api.models.entities.Pickup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupRepository extends JpaRepository<Pickup, Integer> {

    Page<Pickup> findAllByDriverId(int driverId, Pageable pageable);
}
