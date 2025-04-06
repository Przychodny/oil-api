package com.example.oil_api.services;

import com.example.oil_api.mappers.DailyRegisterMapper;
import com.example.oil_api.models.command.create.CreateDailyRegisterCommand;
import com.example.oil_api.models.dto.DailyRegisterDto;
import com.example.oil_api.models.entities.DailyRegister;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Expense;
import com.example.oil_api.models.entities.Pickup;
import com.example.oil_api.repositories.DailyRegisterRepository;
import com.example.oil_api.repositories.DriverRepository;
import com.example.oil_api.repositories.UpdateBalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyRegisterService {

    private final DriverRepository driverRepository;
    private final DailyRegisterMapper dailyRegisterMapper;
    private final DailyRegisterRepository dailyRegisterRepository;
    private final UpdateBalanceRepository updateBalanceRepository;

    @Transactional
    public DailyRegisterDto create(int driverId, CreateDailyRegisterCommand command) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        Set<Pickup> dailyPickups = driver.getPickups().stream() //METODY!!!! z uzyciem typow generycznych jesli trzeba
                .filter(p -> p.getPickupTime() != null && p.getPickupTime().toLocalDate().equals(command.getLocalDate()))
                .collect(Collectors.toSet());

        Set<Expense> dailyExpenses = driver.getExpenses().stream()
                .filter(e -> e.getLocalDateTime() != null && e.getLocalDateTime().toLocalDate().equals(command.getLocalDate()))
                .collect(Collectors.toSet());

        BigDecimal grossAmountSpent = dailyPickups.stream() //METODY!!!! z uzyciem typow generycznych
                .map(Pickup::getGrossTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOilCollected = dailyPickups.stream()
                .map(Pickup::getKg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netAdditionalExpenses = dailyExpenses.stream()
                .map(Expense::getNetAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grossAdditionalExpenses = dailyExpenses.stream()
                .map(Expense::getGrossAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal finalBalance = driver.getBalance();
        BigDecimal startingBalance = getStartingBalance(driver, grossAmountSpent, grossAdditionalExpenses, command.getLocalDate());

        DailyRegister register = dailyRegisterMapper.mapFromCommand(command);
        register.setDriver(driver);
        register.setPickups(dailyPickups); //nie no co za mapper ktory nie mapuje? :D
        register.setExpenses(dailyExpenses);
        register.setGrossAmountSpent(grossAmountSpent);
        register.setTotalOilCollected(totalOilCollected);
        register.setNetAdditionalExpenses(netAdditionalExpenses);
        register.setStartingBalance(startingBalance);
        register.setFinalBalance(finalBalance);
        register.setDate(command.getLocalDate());

        DailyRegister savedRegister = dailyRegisterRepository.save(register);
        return dailyRegisterMapper.mapToDto(savedRegister);
    }

    @Transactional(readOnly = true)
    public DailyRegisterDto findById(int id) {
        return dailyRegisterRepository.findById(id)
                .map(dailyRegisterMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Register not found"));
    }

    @Transactional(readOnly = true)
    public Page<DailyRegisterDto> getAllByDriver(int driverId, Pageable pageable) {
        return dailyRegisterRepository.findAllByDriverId(driverId, pageable)
                .map(dailyRegisterMapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public Page<DailyRegisterDto> getAll(Pageable pageable) {
        return dailyRegisterRepository.findAll(pageable)
                .map(dailyRegisterMapper::mapToDto);
    }

    @Transactional
    public void deleteById(int id) {
        dailyRegisterRepository.deleteById(id);
    }

    private BigDecimal getStartingBalance(Driver driver, BigDecimal grossAmountSpent, BigDecimal grossAdditionalExpenses,
                                          LocalDate date) {
        BigDecimal addedBalance = updateBalanceRepository
                .findSumByDriverIdAndOperationAndDate(driver.getId(), "add", date);
        BigDecimal subtractedBalance = updateBalanceRepository
                .findSumByDriverIdAndOperationAndDate(driver.getId(), "subtract", date);

        BigDecimal netBalanceModification = (addedBalance != null ? addedBalance : BigDecimal.ZERO)
                .subtract(subtractedBalance != null ? subtractedBalance : BigDecimal.ZERO);

        return driver.getBalance()
                .add(grossAmountSpent)
                .add(grossAdditionalExpenses)
                .subtract(netBalanceModification);
    }
}