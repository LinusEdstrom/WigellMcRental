package com.edstrom.WigellMcRental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record McCreateDto(

        @NotBlank String model,
        @NotBlank String name,
        @NotBlank String year,
        @NotNull BigDecimal pricePerDay

) {



}
