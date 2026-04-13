package com.edstrom.WigellMcRental.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookingCreateDto(

        @NotNull Long customerId,
        @NotEmpty List<Long> motorcycleIds,
        @NotNull LocalDate rentalDate,
        @NotNull LocalDate returnDate,
        @NotNull BigDecimal totalPrice

) {
}
