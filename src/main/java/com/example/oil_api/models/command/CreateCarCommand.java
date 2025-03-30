package com.example.oil_api.models.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarCommand {

    private String model;
    private String brand;
    private String registration;
}
