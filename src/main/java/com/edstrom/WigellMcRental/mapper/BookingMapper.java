package com.edstrom.WigellMcRental.mapper;

import com.edstrom.WigellMcRental.dto.BookingCreateDto;
import com.edstrom.WigellMcRental.dto.BookingDto;
import com.edstrom.WigellMcRental.model.Booking;
import com.edstrom.WigellMcRental.model.Customer;
import com.edstrom.WigellMcRental.model.Mc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class BookingMapper {

        private BookingMapper(){}

        // Create Booking entity from DTO and Customer and motorcycle list
        public static Booking createBooking(Customer customer, List<Mc> motorcycles, BookingCreateDto dto) {
            Booking booking = new Booking();
            booking.setCustomer(customer);
            booking.setMcs(motorcycles);
            booking.setRentalDate(dto.rentalDate());
            booking.setReturnDate(dto.returnDate());
            booking.setTotalPrice(dto.totalPrice());

            return booking;
        }

        // Map entity Booking to BookingDTO
        public static BookingDto toDto(Booking booking) {
            List<Long> motorcycleIds = booking.getMcs().stream()
                    .map(Mc::getId)
                    .toList();
            return new BookingDto(
                    booking.getId(),
                    booking.getCustomer().getId(),
                    motorcycleIds,
                    booking.getRentalDate(),
                    booking.getReturnDate(),
                    booking.getTotalPrice().setScale(2, RoundingMode.HALF_UP),
                    booking.getTotalPriceGbp().setScale(2, RoundingMode.HALF_UP),
                    booking.getStatus()
            );
        }
    }



