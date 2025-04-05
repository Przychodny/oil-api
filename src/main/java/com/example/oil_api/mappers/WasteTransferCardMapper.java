package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.WasteTransferCardDto;
import com.example.oil_api.models.entities.WasteTransferCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WasteTransferCardMapper {

    @Mapping(target = "clientId", source = "client.id")
    WasteTransferCardDto mapToDto(WasteTransferCard wasteTransferCard);
}
