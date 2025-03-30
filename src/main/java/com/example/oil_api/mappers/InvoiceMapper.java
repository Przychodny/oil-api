package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.InvoiceDto;
import com.example.oil_api.models.entities.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceDto mapToDto(Invoice invoice);
}
