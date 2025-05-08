package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.DriverDto;
import com.example.oil_api.models.entities.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CarMapper.class)
public interface DriverMapper {

    @Mapping(target = "carId", source = "car.id")
    DriverDto mapToDto(Driver driver);
}
