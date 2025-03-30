package com.example.oil_api.models.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignCarToDriverCommand {

    private Integer driverId;
    private Integer carId;

}
