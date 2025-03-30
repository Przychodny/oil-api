package com.example.oil_api.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;

}
