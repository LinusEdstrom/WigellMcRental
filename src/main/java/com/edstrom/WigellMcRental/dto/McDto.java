package com.edstrom.WigellMcRental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record McDto(

        Long id,
         String model,
         String name,
         String year,
         BigDecimal pricePerDay
) {
}
