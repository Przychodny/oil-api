package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.WasteTransferCardDto;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Pickup;
import com.example.oil_api.models.entities.WasteTransferCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WasteTransferCardMapper {

    @Mapping(target = "clientId", source = "client.id")
    WasteTransferCardDto mapToDto(WasteTransferCard wasteTransferCard);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "pickup.client")
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "driverCarRegistration", source = "driver.car.registration")
    WasteTransferCard mapFromPickup(Pickup pickup, Driver driver);
}
