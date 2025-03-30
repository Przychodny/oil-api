package com.example.oil_api.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class ClientDto {

    private Integer id;
    private String name;
    private String nip;
    private String address;
}
