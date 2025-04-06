package com.example.oil_api.services;

import com.example.oil_api.mappers.CarMapper;
import com.example.oil_api.models.command.AssignCarToDriverCommand;
import com.example.oil_api.models.command.create.CreateCarCommand;
import com.example.oil_api.models.dto.CarDto;
import com.example.oil_api.models.entities.Car;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.repositories.CarRepository;
import com.example.oil_api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final UserRepository userRepository;

    public CarDto create(CreateCarCommand command) {
        Car car = carMapper.mapFromCommand(command);
        car.setBrand(command.getBrand()); //czemu to nie jest w mapperze? Mapstructem bez problemu sie to zrobi
        car.setModel(command.getModel());
        car.setRegistration(command.getRegistration());
        return carMapper.mapToDto(carRepository.save(car));
    }

    public CarDto assignCarToDriver(AssignCarToDriverCommand command) {
        Car car = carRepository.findById(command.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Driver driver = (Driver) userRepository.findById(command.getDriverId()) //rzutowanie w tej formie? Nope, bo wywali Ci exception
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

//        var driver = userRepository.findById(command.getDriverId())
//                .filter(Driver.class::isInstance)
//                .map(Driver.class::cast)
//                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        if (driver.getCar() != null) { //powinnismy tak to pierw wyciagnac drivera i zwalidowac czy nie ma samochodu + to moglaby byc metoda w driverze albo tutaj
            throw new IllegalArgumentException("Driver already has a car assigned.");
        }

        driver.setCar(car);
        userRepository.save(driver);
        return carMapper.mapToDto(car); //czemu zwracany jest carDto skoro to jest funkcja assign?
    }
}
