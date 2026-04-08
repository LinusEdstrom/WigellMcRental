package com.edstrom.WigellMcRental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record McDto(

        Long id,
         String model,
         String name,
         String year,
         Long pricePerDay
) {
}
