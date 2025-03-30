package com.example.oil_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PickupDto {

    private Integer id;
    private Integer driverId;
    private Integer clientId;
    private Integer invoiceId;
    private Integer wasteCardId;
    private LocalDateTime pickupTime;
    private BigDecimal netPricePerKg;
    private BigDecimal kg;
    private BigDecimal netTotal;
    private BigDecimal vat;
    private BigDecimal grossTotal;
}
