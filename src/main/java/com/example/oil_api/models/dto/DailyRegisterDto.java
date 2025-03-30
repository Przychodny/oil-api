package com.example.oil_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyRegisterDto {

    private Integer id;
    private Integer driverId;
    private LocalDate date;
    private BigDecimal startingBalance;
    private BigDecimal grossAmountSpent;
    private BigDecimal totalOilCollected;
    private BigDecimal netAdditionalExpenses;
    private BigDecimal finalBalance;
    private Set<PickupDto> pickups = new HashSet<>();
    private Set<ExpenseDto> expenses = new HashSet<>();

}
