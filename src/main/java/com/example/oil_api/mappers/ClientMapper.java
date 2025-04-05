package com.example.oil_api.mappers;

import com.example.oil_api.models.command.create.CreateClientCommand;
import com.example.oil_api.models.dto.ClientDto;
import com.example.oil_api.models.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client mapFromCommand(CreateClientCommand command);
    ClientDto mapToDto(Client client);
}
