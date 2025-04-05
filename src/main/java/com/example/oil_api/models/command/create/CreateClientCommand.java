package com.example.oil_api.models.command.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientCommand {

    private String nip;
    private String name;
    private String address;

}
