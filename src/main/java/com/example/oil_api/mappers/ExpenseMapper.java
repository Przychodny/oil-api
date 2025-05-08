package com.example.oil_api.mappers;

import com.example.oil_api.models.command.create.CreateExpenseCommand;
import com.example.oil_api.models.dto.ExpenseDto;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "localDateTime", expression = "java(java.time.LocalDateTime.now())")
    Expense mapFromCommand(CreateExpenseCommand command,
                           Driver driver,
                           BigDecimal netAmount,
                           BigDecimal vatPercentage,
                           BigDecimal grossAmount);

    ExpenseDto mapToDto(Expense expense);
}
