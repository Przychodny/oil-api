package com.example.oil_api.mappers;

import com.example.oil_api.models.command.create.CreateDailyRegisterCommand;
import com.example.oil_api.models.dto.DailyRegisterDto;
import com.example.oil_api.models.entities.DailyRegister;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Expense;
import com.example.oil_api.models.entities.Pickup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {PickupMapper.class,  ExpenseMapper.class})
public interface DailyRegisterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", source = "driver")
    DailyRegister mapFromCommand(
            CreateDailyRegisterCommand command,
            Driver driver,
            Set<Pickup> pickups,
            Set<Expense> expenses,
            BigDecimal grossAmountSpent,
            BigDecimal totalOilCollected,
            BigDecimal netAdditionalExpenses,
            BigDecimal startingBalance,
            BigDecimal finalBalance
    );

    @Mapping(source = "driver.id", target = "driverId")
    DailyRegisterDto mapToDto(DailyRegister dailyRegister);
}
