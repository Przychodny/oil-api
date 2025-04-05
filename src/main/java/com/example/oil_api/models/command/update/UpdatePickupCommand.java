package com.example.oil_api.models.command.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePickupCommand {

    private Integer clientId;
    private BigDecimal netPricePerKg;
    private BigDecimal kg;
    private BigDecimal netTotal;
    private BigDecimal vat;
    private BigDecimal grossTotal;
}
