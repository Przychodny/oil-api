package com.example.oil_api.models.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {

    private String type;
    private Map<String, String> params;
}
