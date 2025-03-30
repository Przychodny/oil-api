package com.example.oil_api.mappers;

import com.example.oil_api.models.command.CreateDailyRegisterCommand;
import com.example.oil_api.models.dto.DailyRegisterDto;
import com.example.oil_api.models.entities.DailyRegister;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PickupMapper.class})
public interface DailyRegisterMapper {

    DailyRegister mapFromCommand(CreateDailyRegisterCommand command);

    @Mapping(source = "driver.id", target = "driverId")
    DailyRegisterDto mapToDto(DailyRegister dailyRegister);
}
