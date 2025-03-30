package com.example.oil_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExpenseDto {

    private Integer id;
    private BigDecimal netAmount;
    private BigDecimal vatPercentage;
    private BigDecimal grossAmount;
    private String description;
    private LocalDateTime localDateTime;
}
