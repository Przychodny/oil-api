package com.example.oil_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBalanceDto {

    private Integer id;
    private Integer driverId;
    private BigDecimal modificationAmount;
    private String modifier;
    private LocalDate modificationDate;
}
