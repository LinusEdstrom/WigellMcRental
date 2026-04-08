package com.edstrom.WigellMcRental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotorcycleAvailableDto(

    @NotBlank String model,
    @NotBlank String name,
    @NotBlank String year,
    @NotNull Long pricePerDay
) {
}
