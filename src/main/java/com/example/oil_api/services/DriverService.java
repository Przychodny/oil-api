package com.example.oil_api.services;

import com.example.oil_api.mappers.DriverMapper;
import com.example.oil_api.models.dto.DriverDto;
import com.example.oil_api.repositories.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;


    @Transactional(readOnly = true)
    public DriverDto getById(int id) {
        return driverRepository.findById(id)
                .map(driverMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
    }
}
