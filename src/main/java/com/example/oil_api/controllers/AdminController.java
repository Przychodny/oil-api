package com.example.oil_api.controllers;

import com.example.oil_api.models.command.AssignCarToDriverCommand;
import com.example.oil_api.models.command.create.CreateCarCommand;
import com.example.oil_api.models.command.update.UpdateBalanceCommand;
import com.example.oil_api.models.dto.CarDto;
import com.example.oil_api.models.dto.ClientDto;
import com.example.oil_api.models.dto.DailyRegisterDto;
import com.example.oil_api.models.dto.InvoiceDto;
import com.example.oil_api.models.dto.PickupDto;
import com.example.oil_api.models.dto.UpdateBalanceDto;
import com.example.oil_api.models.dto.WasteTransferCardDto;
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
//imo paczka admin i kontrollery - klania sie zasada "S" z SOLID - single responsibility
    private final PickupService pickupService;
    private final ClientService clientService;
    private final CarService carService;
    private final UpdateBalanceService balanceModificationService;
    private final DailyRegisterService dailyRegisterService;
    private final UpdateBalanceService updateBalanceService;

    @GetMapping("/pickups")
    public Page<PickupDto> getAllPickups(Pageable pageable) {
        return pickupService.getAll(pageable);
    }

    @GetMapping("/pickups/{driverId}")
    public Page<PickupDto> getAllPickupsByDriver(@PathVariable int driverId, Pageable pageable) {
        return pickupService.getAllByDriver(driverId, pageable);
    }

    @GetMapping("/pickups/{pickupId}/invoice")
    public InvoiceDto getInvoiceByPickup(@PathVariable int pickupId) {
        return pickupService.getInvoiceByPickupId(pickupId);
    }

    @GetMapping("/pickups/{pickupId}/wasteCard")
    public WasteTransferCardDto getWasteCardByPickup(@PathVariable int pickupId) {
        return pickupService.getWasteCardByPickupId(pickupId);
    }

    @GetMapping("/register")
    public Page<DailyRegisterDto> getAllDailyRegistrations(Pageable pageable) {
        return dailyRegisterService.getAll(pageable);
    }

    @GetMapping("/register/{driverId}")
    public Page<DailyRegisterDto> getAllDailyRegistrationsByDriver(@PathVariable int driverId, Pageable pageable) {
        return dailyRegisterService.getAllByDriver(driverId, pageable);
    }

    @GetMapping("/updateBalance")
    public Page<UpdateBalanceDto> getAll(Pageable pageable) {
        return updateBalanceService.getAll(pageable);
    }

    @GetMapping("updateBalance/{driverId}")
    public Page<UpdateBalanceDto> getAllByDriver(@PathVariable int driverId, Pageable pageable) {
        return updateBalanceService.getAllByDriver(driverId, pageable);
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
