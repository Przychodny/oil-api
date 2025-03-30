package com.example.oil_api.models.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CreateExpenseCommand {

    private BigDecimal vatPercentage;
    private BigDecimal grossAmount;
    private String description;
}
