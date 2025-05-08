package com.example.oil_api.models.command.create;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateDailyRegisterCommand {

    private LocalDate date;
}
