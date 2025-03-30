package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.DriverDto;
import com.example.oil_api.models.entities.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    @Mapping(target = "carDto", source = "car")
    @Mapping(target = "balance", source = "balance")
    DriverDto mapToDto(Driver driver);
}
