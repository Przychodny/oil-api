package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.InvoiceDto;
import com.example.oil_api.models.entities.Invoice;
import com.example.oil_api.models.entities.Pickup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "pickup.client")
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    Invoice mapFromPickup(Pickup pickup);

    @Mapping(target = "clientId", source = "client.id")
    InvoiceDto mapToDto(Invoice invoice);
}
