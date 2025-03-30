package com.example.oil_api.mappers;

import com.example.oil_api.models.dto.WasteTransferCardDto;
import com.example.oil_api.models.entities.WasteTransferCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WasteTransferCardMapper {

    WasteTransferCardDto mapToDto(WasteTransferCard wasteTransferCard);
}
