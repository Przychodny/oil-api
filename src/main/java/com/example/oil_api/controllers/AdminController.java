package com.example.oil_api.controllers;

import com.example.oil_api.models.command.AssignCarToDriverCommand;
import com.example.oil_api.models.command.CreateCarCommand;
import com.example.oil_api.models.command.UpdateBalanceCommand;
import com.example.oil_api.models.dto.CarDto;
import com.example.oil_api.models.dto.ClientDto;
import com.example.oil_api.models.dto.DailyRegisterDto;
import com.example.oil_api.models.dto.PickupDto;
import com.example.oil_api.models.dto.UpdateBalanceDto;
import com.example.oil_api.services.CarService;
import com.example.oil_api.services.ClientService;
import com.example.oil_api.services.DailyRegisterService;
import com.example.oil_api.services.PickupService;
import com.example.oil_api.services.UpdateBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final PickupService pickupService;
    private final ClientService clientService;
    private final CarService carService;
    private final UpdateBalanceService balanceModificationService;
    private final DailyRegisterService dailyRegisterService;


    @GetMapping("/pickups")
    public Page<PickupDto> getAllPickups(Pageable pageable) {
        return pickupService.getAll(pageable);
    }

    @GetMapping("/pickups/{driverId}")
    public Page<PickupDto> getAllPickupsByDriver(@PathVariable int driverId, Pageable pageable) {
        return pickupService.getAllByDriver(driverId, pageable);
    }

    @GetMapping("/dailyRegistrations")
    public Page<DailyRegisterDto> getAllDailyRegistrations(Pageable pageable) {
        return dailyRegisterService.getAll(pageable);
    }

    @GetMapping("/dailyRegistrations/{driverId}")
    public Page<DailyRegisterDto> getAllDailyRegistrationsByDriver(@PathVariable int driverId, Pageable pageable) {
        return dailyRegisterService.getAllByDriver(driverId, pageable);
    }

    @GetMapping("/clients")
    public Page<ClientDto> getAllClients(Pageable pageable) {
        return clientService.getAll(pageable);
    }


    @PatchMapping("/{driverId}/add")
    public UpdateBalanceDto addToBalance(@PathVariable int driverId, @RequestBody UpdateBalanceCommand command) {
        return balanceModificationService.addToBalanceForDriver(driverId, command);
    }

    @PatchMapping("/{driverId}/subtract")
    public UpdateBalanceDto subtractFromBalance(@PathVariable int driverId, @RequestBody UpdateBalanceCommand command) {
        return balanceModificationService.subtractToBalanceForDriver(driverId, command);
    }

    @PostMapping("/car")
    public CarDto createCar(@RequestBody CreateCarCommand command) {
        return carService.create(command);
    }

    @PostMapping("/assign")
    public CarDto assignCarToDriver(@RequestBody AssignCarToDriverCommand command) {
        return carService.assignCarToDriver(command);
    }
}
