package com.edstrom.WigellMcRental.dto;

import java.time.LocalDate;
import java.util.List;

public record BookingDto(

        Long bookingId,
        Long customerId,
        List<Long> motorcycleIds,
        LocalDate rentalDate,
        LocalDate returnDate

) {
}
