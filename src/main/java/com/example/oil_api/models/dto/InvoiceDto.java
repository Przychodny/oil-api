package com.example.oil_api.models.dto;

import com.example.oil_api.models.entities.Client;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {

    private Integer id;
    private Integer clientId;
    private String number;
    private LocalDate date;
    private LocalDateTime pickupTime;
    private BigDecimal netPricePerKg;
    private BigDecimal kg;
    private BigDecimal netTotal;
    private BigDecimal vat;
    private BigDecimal grossTotal;
}
