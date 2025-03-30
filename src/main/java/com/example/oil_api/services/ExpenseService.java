package com.example.oil_api.services;

import com.example.oil_api.mappers.ExpenseMapper;
import com.example.oil_api.models.command.CreateExpenseCommand;
import com.example.oil_api.models.dto.ExpenseDto;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Expense;
import com.example.oil_api.repositories.DriverRepository;
import com.example.oil_api.repositories.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final DriverRepository driverRepository;

    public ExpenseDto add(int driverId, CreateExpenseCommand command) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        BigDecimal gross = command.getGrossAmount();
        BigDecimal vat = calculateVat(gross, command.getVatPercentage());
        BigDecimal net = gross.subtract(vat);

        Expense expense = expenseMapper.mapFromCommand(command);
        expense.setVatPercentage(vat);
        expense.setNetAmount(net);
        expense.setDriver(driver);
        expense.setLocalDateTime(LocalDateTime.now());

        driver.setBalance(driver.getBalance().subtract(gross));
        driverRepository.save(driver);

        return expenseMapper.mapToDto(expenseRepository.save(expense));
    }

    public ExpenseDto findById(int id) {
        return expenseRepository.findById(id)
                .map(expenseMapper::mapToDto)
                .orElseThrow(()-> new EntityNotFoundException("Expense not found"));
    }
    private BigDecimal calculateVat(BigDecimal gross, BigDecimal vatPercentage) {
        return gross.multiply(vatPercentage)
                .divide(vatPercentage.add(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
    }


}
