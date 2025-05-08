package com.example.oil_api.services;

import com.example.oil_api.common.BalanceModifier;
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
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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

        Set<Pickup> dailyPickups = filterByDate(driver.getPickups(), Pickup::getPickupTime, command.getDate());
        Set<Expense> dailyExpenses = filterByDate(driver.getExpenses(), Expense::getLocalDateTime, command.getDate());

        BigDecimal grossAmountSpent = getTotal(dailyPickups, Pickup::getGrossTotal);
        BigDecimal totalOilCollected = getTotal(dailyPickups, Pickup::getKg);
        BigDecimal netAdditionalExpenses = getTotal(dailyExpenses, Expense::getNetAmount);
        BigDecimal grossAdditionalExpenses = getTotal(dailyExpenses, Expense::getGrossAmount);

        BigDecimal finalBalance = driver.getBalance();
        BigDecimal startingBalance = getStartingBalance(driver, grossAmountSpent, grossAdditionalExpenses, command.getDate());

        DailyRegister register = dailyRegisterMapper.mapFromCommand(
                command,
                driver,
                dailyPickups,
                dailyExpenses,
                grossAmountSpent,
                totalOilCollected,
                netAdditionalExpenses,
                startingBalance,
                finalBalance
        );

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
                .findSumByDriverIdAndOperationAndDate(driver.getId(), BalanceModifier.ADD, date);
        BigDecimal subtractedBalance = updateBalanceRepository
                .findSumByDriverIdAndOperationAndDate(driver.getId(), BalanceModifier.SUBTRACT, date);

        BigDecimal netBalanceModification = (addedBalance != null ? addedBalance : BigDecimal.ZERO)
                .subtract(subtractedBalance != null ? subtractedBalance : BigDecimal.ZERO);

        return driver.getBalance()
                .add(grossAmountSpent)
                .add(grossAdditionalExpenses)
                .subtract(netBalanceModification);
    }

    private <T> Set<T> filterByDate(Collection<T> items, Function<T, LocalDateTime> dateExtractor, LocalDate localDate) {
        return items.stream()
                .filter(item -> {
                    LocalDateTime itemDate = dateExtractor.apply(item);
                    return itemDate != null
                            && itemDate.toLocalDate().equals(localDate);
                })
                .collect(Collectors.toSet());
    }

    private <T> BigDecimal getTotal(Collection<T> items, Function<T, BigDecimal> mapper) {
        return items.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}