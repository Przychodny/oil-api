package com.example.oil_api.mappers;

import com.example.oil_api.models.command.CreateExpenseCommand;
import com.example.oil_api.models.dto.ExpenseDto;
import com.example.oil_api.models.entities.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    Expense mapFromCommand(CreateExpenseCommand command);

    ExpenseDto mapToDto(Expense expense);

}
