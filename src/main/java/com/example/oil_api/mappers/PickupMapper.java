package com.example.oil_api.mappers;

import com.example.oil_api.models.command.create.CreatePickupCommand;
import com.example.oil_api.models.dto.PickupDto;
import com.example.oil_api.models.entities.Client;
import com.example.oil_api.models.entities.Driver;
import com.example.oil_api.models.entities.Pickup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface PickupMapper {

    @Mapping(target = "id", ignore = true)
    Pickup mapToEntity(CreatePickupCommand command,
                       Driver driver,
                       Client client,
                       BigDecimal netTotal,
                       BigDecimal vat,
                       BigDecimal grossTotal);

    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "invoiceId", source = "invoice.id")
    @Mapping(target = "wasteCardId", source = "wasteTransferCard.id")
    PickupDto mapToDto(Pickup pickup);
}
