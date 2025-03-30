package com.example.oil_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteTransferCardDto {

    private Integer id;
    private Integer clientId;
    private String number;
    private LocalDateTime localDateTime;
    private BigDecimal weightMg;
    private String driverCarRegistration;
}
