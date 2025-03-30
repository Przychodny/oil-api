package com.example.oil_api.models.dto;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;


@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class AdminDto extends UserDto {
}
