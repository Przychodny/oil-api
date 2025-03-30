package com.example.oil_api.models.dto;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class DriverDto extends UserDto {

    private CarDto carDto;
    private BigDecimal balance;
}
