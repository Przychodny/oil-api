package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.InvoiceDto;
import com.example.oil_api.models.entities.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "pickupTime", source = "date")
    InvoiceDto mapToDto(Invoice invoice);
}
