package com.example.oil_api.models.command.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePickupCommand {

    private Integer clientId;
    private BigDecimal netPricePerKg;
    private BigDecimal kg;
}
