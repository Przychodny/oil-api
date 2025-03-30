package com.example.oil_api.mappers;

import com.example.oil_api.models.command.CreatePickupCommand;
import com.example.oil_api.models.dto.PickupDto;
import com.example.oil_api.models.entities.Pickup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PickupMapper {

    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "wasteTransferCard.id", target = "wasteCardId")
    PickupDto mapToDto(Pickup pickup);

    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "client", ignore = true)
    Pickup mapFromCommand(CreatePickupCommand command);
}
