package com.example.oil_api.services;

import com.example.oil_api.common.BalanceModifier;
import com.example.oil_api.mappers.UpdateBalanceMapper;
import com.example.oil_api.models.command.update.UpdateBalanceCommand;
import com.example.oil_api.models.dto.UpdateBalanceDto;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.UpdateBalance;
import com.example.oil_api.repositories.DriverRepository;
import com.example.oil_api.repositories.UpdateBalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<UpdateBalanceDto> getAll(Pageable pageable) {
        return updateBalanceRepository.findAll(pageable)
                .map(updateBalanceMapper::mapToDto);
    }

    public Page<UpdateBalanceDto> getAllByDriver(int driverId, Pageable pageable) {
        return updateBalanceRepository.findAllByDriver(driverId, pageable)
                .map(updateBalanceMapper::mapToDto);
    }


    public UpdateBalanceDto addToBalanceForDriver(int driverId, UpdateBalanceCommand command) {
        return updateBalanceForDriver(driverId, BalanceModifier.ADD, command, BigDecimal::add);
    }

    public UpdateBalanceDto subtractToBalanceForDriver(int driverId, UpdateBalanceCommand command) {
        return updateBalanceForDriver(driverId, BalanceModifier.SUBTRACT, command, BigDecimal::subtract);
    }

    private UpdateBalanceDto updateBalanceForDriver(int driverId, BalanceModifier modifier, UpdateBalanceCommand command,
                                                    BiFunction<BigDecimal, BigDecimal, BigDecimal> operation) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        BigDecimal newBalance = operation.apply(driver.getBalance(), command.getAmount());
        driver.setBalance(newBalance);
        driverRepository.save(driver);

        UpdateBalance updateBalance = updateBalanceMapper.mapFromCommand(command);
        updateBalance.setDriver(driver);
        updateBalance.setModificationAmount(command.getAmount());
        updateBalance.setModificationDate(LocalDate.now());
        updateBalance.setModifier(modifier);

        return updateBalanceMapper.mapToDto(updateBalanceRepository.save(updateBalance));
    }
}
