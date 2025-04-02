package com.example.oil_api.models.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateDailyRegisterCommand {

    private LocalDate localDate;
}
