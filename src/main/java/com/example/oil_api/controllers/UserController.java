package com.example.oil_api.controllers;

import com.example.oil_api.models.dto.ClientDto;
import com.example.oil_api.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final ClientService clientService;

    @GetMapping("/nip{nip}")
    public ClientDto getClientByNip(@PathVariable String nip) {
        return clientService.createByNip(nip);
    }

    @GetMapping("/search")
    public Page<ClientDto> searchClients(Pageable pageable, @RequestParam String query) {
        return clientService.findBy(pageable, query);
    }
}
