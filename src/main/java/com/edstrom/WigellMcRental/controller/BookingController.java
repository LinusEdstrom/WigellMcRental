package com.edstrom.WigellMcRental.controller;

import com.edstrom.WigellMcRental.dto.BookingCreateDto;
import com.edstrom.WigellMcRental.dto.BookingDto;
import com.edstrom.WigellMcRental.dto.BookingPatchDto;
import com.edstrom.WigellMcRental.dto.BookingStatusPatchDto;
import com.edstrom.WigellMcRental.model.Booking;
import com.edstrom.WigellMcRental.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDto> listAll() {return bookingService.findAll();}

    @GetMapping("{id}")
    public BookingDto get(@PathVariable Long id){
        return bookingService.findById(id);
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody @Valid BookingCreateDto dto) {
        BookingDto booking = bookingService.createBooking(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(booking.bookingId())
                .toUri();

        return ResponseEntity.created(location).body(booking);
    }

    /*@PatchMapping("/{bookingId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookingDto> patchBooking(
            @PathVariable Long bookingId,
            @RequestBody(required = false) BookingPatchDto dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.patchBooking(bookingId, dto, authentication)
        );
    }

     */
    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> completeBooking(@PathVariable Long bookingId){
        BookingDto updatedBooking = bookingService.completeBooking(bookingId);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
