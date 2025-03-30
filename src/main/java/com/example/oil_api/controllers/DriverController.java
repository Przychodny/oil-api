package com.example.oil_api.controllers;

import com.example.oil_api.models.command.CreateDailyRegisterCommand;
import com.example.oil_api.models.command.CreateExpenseCommand;
import com.example.oil_api.models.command.CreatePickupCommand;
import com.example.oil_api.models.dto.DailyRegisterDto;
import com.example.oil_api.models.dto.DriverDto;
import com.example.oil_api.models.dto.ExpenseDto;
import com.example.oil_api.models.dto.PickupDto;
import com.example.oil_api.services.DailyRegisterService;
import com.example.oil_api.services.DriverService;
import com.example.oil_api.services.ExpenseService;
import com.example.oil_api.services.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;
    private final PickupService pickupService;
    private final ExpenseService expenseService;
    private final DailyRegisterService dailyRegisterService;

    @GetMapping("/{id}")
    public DriverDto getById(@PathVariable int id) {
        return driverService.getById(id);
    }

    @GetMapping("/{id}/pickups")
    public Page<PickupDto> getAllPickups(@PathVariable int id, Pageable pageable) {
        return pickupService.getAllByDriver(id, pageable);
    }

    @PostMapping("/{id}/pickups")
    public PickupDto createPickup(@PathVariable int id, @RequestBody CreatePickupCommand command) {
        return pickupService.create(id, command);
    }

    @PostMapping("/{id}/expenses")
    public ExpenseDto add(@PathVariable int id, @RequestBody CreateExpenseCommand command) {
        return expenseService.add(id, command);
    }

    @PostMapping("/{id}/register")
    public DailyRegisterDto create(@PathVariable int id, @RequestBody CreateDailyRegisterCommand command) {
        return dailyRegisterService.create(id, command);
    }

    @GetMapping("/register/{id}")
    public DailyRegisterDto findById(@PathVariable int id) {
        return dailyRegisterService.findById(id);
    }

    @DeleteMapping("/register/{id}")
    public void deleteById(@PathVariable int id) {
        dailyRegisterService.deleteById(id);
    }

    @GetMapping("/expense/{id}")
    public ExpenseDto findExpenseById(@PathVariable int id) {
        return expenseService.findById(id);
    }
}
