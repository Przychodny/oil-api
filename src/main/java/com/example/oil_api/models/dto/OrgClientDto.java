package com.example.oil_api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgClientDto {
    private NameDto nazwy;
    private AddressDto adres;


    @Getter
    @Setter
    @Builder //po co dajesz te wszystkie adnotacje skoro ich nie uzywasz?
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameDto {
        private String pelna;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDto {
        private String kod; //imo ja bym zachowal angielskie nazwy i uzyl @JsonProperty
        private String miejscowosc;
        private String nr_domu;
        private String ulica;
        private String panstwo;
    }
}
