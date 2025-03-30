package com.example.oil_api.models.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PickupSearchCriteria {

    private Integer driverId;
    private String invoiceFilter;
    private String sortBy = "pickupTime";
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
