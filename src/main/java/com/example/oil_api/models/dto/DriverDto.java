package com.example.oil_api.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class DriverDto extends UserDto {

    private Integer carId;
    private BigDecimal balance;
}
