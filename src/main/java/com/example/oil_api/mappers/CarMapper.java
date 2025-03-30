package com.example.oil_api.mappers;

import com.example.oil_api.models.command.CreateCarCommand;
import com.example.oil_api.models.dto.CarDto;
import com.example.oil_api.models.entities.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car mapFromCommand(CreateCarCommand command);
    CarDto mapToDto(Car car);
}
