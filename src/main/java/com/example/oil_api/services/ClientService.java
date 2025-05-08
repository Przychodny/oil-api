package com.example.oil_api.services;

import com.example.oil_api.client.OrgClient;
import com.example.oil_api.mappers.ClientMapper;
import com.example.oil_api.models.command.create.CreateClientCommand;
import com.example.oil_api.models.dto.ClientDto;
import com.example.oil_api.models.dto.OrgClientDto;
import com.example.oil_api.models.entities.Client;
import com.example.oil_api.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final OrgClient orgClient;

    public ClientDto createByNip(String nip) {
        String externalId = nip.trim();

        OrgClientDto orgDto = orgClient.getOrganizationData(externalId);

        Client client = new Client();
        client.setNip(nip);
        client.setName(orgDto.getNazwy().getPelna());
        client.setAddress(formatAddress(orgDto.getAdres()));

        client = clientRepository.save(client);
        return clientMapper.mapToDto(client);
    }

    public ClientDto create(CreateClientCommand command) {
        Client client = clientMapper.mapFromCommand(command);
        client.setNip(command.getNip());
        client.setName(command.getName());
        client.setAddress(command.getAddress());
        return clientMapper.mapToDto(clientRepository.save(client));
    }

    public Page<ClientDto> findBy(Pageable pageable, String name) {
        return clientRepository.findByCombinedFields(pageable, name)
                .map(clientMapper::mapToDto);
    }


    public Page<ClientDto> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::mapToDto);
    }

    private String formatAddress(OrgClientDto.AddressDto address) {
        return String.format("%s %s, %s %s",
                        address.getUlica() != null ? address.getUlica() : "",
                        address.getNr_domu() != null ? address.getNr_domu() : "",
                        address.getKod() != null ? address.getKod() : "",
                        address.getMiejscowosc() != null ? address.getMiejscowosc() : "")
                .trim();
    }
}
