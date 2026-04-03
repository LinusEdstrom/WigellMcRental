package com.edstrom.WigellMcRental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressCreateDto(


        @NotBlank @Size(max = 20) String street,
        @NotBlank @Size(max = 20)String postalCode,
        @NotBlank @Size(max = 20)String city
) {
}

