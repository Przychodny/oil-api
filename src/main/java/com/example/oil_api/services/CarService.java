package com.example.oil_api.services;

import com.example.oil_api.mappers.CarMapper;
import com.example.oil_api.models.command.AssignCarToDriverCommand;
import com.example.oil_api.models.command.create.CreateCarCommand;
import com.example.oil_api.models.dto.CarDto;
import com.example.oil_api.models.entities.Car;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.repositories.CarRepository;
import com.example.oil_api.repositories.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final DriverRepository driverRepository;


    public CarDto create(CreateCarCommand command) {
        Car car = carMapper.mapFromCommand(command);
        return carMapper.mapToDto(carRepository.save(car));
    }

    @Transactional
    public Car assignCarToDriver(AssignCarToDriverCommand command) {
        Driver driver = driverRepository.findById(command.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        if (driver.getCar() != null) {
            throw new IllegalArgumentException("Driver already has a car assigned.");
        }
        Car car = carRepository.findById(command.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        driver.setCar(car);
        return car;
    }
}
