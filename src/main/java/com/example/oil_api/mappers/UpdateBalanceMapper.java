package com.example.oil_api.mappers;

import com.example.oil_api.models.command.update.UpdateBalanceCommand;
import com.example.oil_api.models.dto.UpdateBalanceDto;
import com.example.oil_api.models.entities.UpdateBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateBalanceMapper {

    UpdateBalance mapFromCommand(UpdateBalanceCommand command);

    @Mapping(source = "driver.id", target = "driverId")
    UpdateBalanceDto mapToDto(UpdateBalance updateBalance);
}
