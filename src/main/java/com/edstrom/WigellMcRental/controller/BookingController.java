package com.edstrom.WigellMcRental.controller;

import com.edstrom.WigellMcRental.dto.BookingCreateDto;
import com.edstrom.WigellMcRental.dto.BookingDto;
import com.edstrom.WigellMcRental.dto.BookingPatchDto;
import com.edstrom.WigellMcRental.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> listBookings(
            @RequestParam(value = "customerId", required = false) Long customerId
    ) {
        List<BookingDto> bookings = bookingService.listBookings(customerId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity <BookingDto> createBooking(@RequestBody BookingCreateDto dto) {
        BookingDto booking = bookingService.createBooking(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
    @PatchMapping("/{bookingId}")
    public ResponseEntity <BookingDto> patchBooking(@PathVariable Long bookingId, @RequestBody BookingPatchDto dto){
        BookingDto booking = bookingService.patchBooking(bookingId, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(booking);
    }

}
