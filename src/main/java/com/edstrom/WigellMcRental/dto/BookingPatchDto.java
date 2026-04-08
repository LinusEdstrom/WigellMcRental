package com.edstrom.WigellMcRental.dto;

import java.time.LocalDate;
import java.util.List;

public record BookingPatchDto(

        List<Long> motorcycleIds,
        LocalDate rentalDate,
        LocalDate returnDate
) {
}
