package com.edstrom.WigellMcRental.dto;

import com.edstrom.WigellMcRental.service.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookingDto(

        Long bookingId,
        Long customerId,
        List<Long> motorcycleIds,
        LocalDate rentalDate,
        LocalDate returnDate,
        BigDecimal totalPrice,
        BigDecimal totalPriceGbp,

        Status status
) {
}
