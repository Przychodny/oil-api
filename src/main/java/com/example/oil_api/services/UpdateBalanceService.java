package com.example.oil_api.services;

import com.example.oil_api.mappers.UpdateBalanceMapper;
import com.example.oil_api.models.command.UpdateBalanceCommand;
import com.example.oil_api.models.dto.UpdateBalanceDto;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.UpdateBalance;
import com.example.oil_api.repositories.DriverRepository;
import com.example.oil_api.repositories.UpdateBalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class UpdateBalanceService {

    private final DriverRepository driverRepository;
    private final UpdateBalanceRepository updateBalanceRepository;
    private final UpdateBalanceMapper updateBalanceMapper;

    public UpdateBalanceDto addToBalanceForDriver(int driverId, UpdateBalanceCommand command) {
        String add = "add";
        return updateBalanceForDriver(driverId, add, command, BigDecimal::add);
    }

    public UpdateBalanceDto subtractToBalanceForDriver(int driverId, UpdateBalanceCommand command) {
        String subtract = "subtract";
        return updateBalanceForDriver(driverId, subtract ,command, BigDecimal::subtract);
    }

    private UpdateBalanceDto updateBalanceForDriver(int driverId, String balanceOperation, UpdateBalanceCommand command,
                                                    BiFunction<BigDecimal, BigDecimal, BigDecimal> operation) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        BigDecimal newBalance = operation.apply(driver.getBalance(), command.getBalance());
        driver.setBalance(newBalance);
        driverRepository.save(driver);

        UpdateBalance updateBalance = updateBalanceMapper.mapFromCommand(command);
        updateBalance.setDriver(driver);
        updateBalance.setModificationAmount(command.getBalance());
        updateBalance.setModificationDate(LocalDate.now());
        updateBalance.setOperation(balanceOperation);

        return updateBalanceMapper.mapToDto(updateBalanceRepository.save(updateBalance));
    }
}
