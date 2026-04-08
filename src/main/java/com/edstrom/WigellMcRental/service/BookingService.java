package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.BookingCreateDto;
import com.edstrom.WigellMcRental.dto.BookingDto;
import com.edstrom.WigellMcRental.dto.BookingPatchDto;
import com.edstrom.WigellMcRental.dto.MotorcycleAvailableDto;
import com.edstrom.WigellMcRental.exception.*;
import com.edstrom.WigellMcRental.mapper.BookingMapper;
import com.edstrom.WigellMcRental.mapper.McMapper;
import com.edstrom.WigellMcRental.mapper.MotorcycleAvailableMapper;
import com.edstrom.WigellMcRental.model.Booking;
import com.edstrom.WigellMcRental.model.Customer;
import com.edstrom.WigellMcRental.model.Mc;
import com.edstrom.WigellMcRental.repository.BookingRepository;
import com.edstrom.WigellMcRental.repository.CustomerRepository;
import com.edstrom.WigellMcRental.repository.McRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final McRepository mcRepository;

    public BookingService(BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          McRepository mcRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.mcRepository = mcRepository;
    }
    @Transactional(readOnly = true)
    public List<BookingDto> listBookings(Long customerId){
        List<Booking> bookings;

        if(customerId !=null){
            bookings = bookingRepository.findByCustomerId(customerId);
        }else {
            bookings = bookingRepository.findAll();
        }
        return bookings.stream()
                .map(BookingMapper::toDto)
                .toList();
    }




    @Transactional
    public BookingDto createBooking(BookingCreateDto dto) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.customerId()));

        validateCustomerAge(customer.getDateOfBirth());

        List<Long> uniqueIds = dto.motorcycleIds()
                .stream()
                .distinct()
                .toList();
        if (dto.motorcycleIds().size() != uniqueIds.size()) {
            throw new IllegalMcDuplicateException();
        }

        List<Mc> motorcycles = uniqueIds.stream()
                .map(mcId -> mcRepository.findById(mcId)
                        .orElseThrow(() -> new McNotFoundException(mcId)))
                .toList();

        checkMotorcyclesAvailability(motorcycles, dto.rentalDate(), dto.returnDate());
        Booking bookingEntity = BookingMapper.createBooking(customer, motorcycles, dto);

        Booking savedBooking = bookingRepository.save(bookingEntity);
        return BookingMapper.toDto(savedBooking);
    }

    @Transactional
    public BookingDto patchBooking(Long bookingId, BookingPatchDto dto) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        LocalDate newStart = dto.rentalDate() != null ? dto.rentalDate() : booking.getRentalDate();
        LocalDate newEnd = dto.returnDate() != null ? dto.returnDate() : booking.getReturnDate();

        if (newEnd.isBefore(newStart)) {
            throw new IllegalDatesException();
        }

        List<Mc> newMotorcycles = booking.getMcs();
        if (dto.motorcycleIds() != null && !dto.motorcycleIds().isEmpty()) {
            newMotorcycles = mcRepository.findAllById(dto.motorcycleIds());
        }

        boolean anyUnavailable = newMotorcycles.stream()
                .anyMatch(mc -> bookingRepository.existsBookingForMcBetweenExcludingBooking(
                        mc.getId(),
                        newStart,
                        newEnd,
                        bookingId
                ));

        if (anyUnavailable) {
            throw new IllegalMcUnavailableException();
        }
        // Apply updates
        booking.setMcs(newMotorcycles);
        booking.setRentalDate(newStart);
        booking.setReturnDate(newEnd);

        bookingRepository.save(booking);

        return BookingMapper.toDto(booking);
    }
        // Kör den här badboyen på alla availability checkar sen
        private void checkMotorcyclesAvailability (List < Mc > motorcycles, LocalDate rentalStart, LocalDate rentalEnd){
            boolean anyUnavailable = motorcycles.stream()
                    .anyMatch(mc -> bookingRepository.existsBookingForMcBetween(mc.getId(), rentalStart, rentalEnd));

            if (anyUnavailable) {
                throw new IllegalMcUnavailableException();
            }
        }
        //Hämta alla frågan är om man behöver den ?? Mycket nu alltså
        public List<MotorcycleAvailableDto> findAvailableMotorcycles (LocalDate from, LocalDate to){

            // Filtrera bort motorcyklar som är upptagna
            List<Mc> available = mcRepository.findAvailableMotorcycles(from, to);

            // Konvertera till DTO
            return available.stream()
                    .map(MotorcycleAvailableMapper::toDto)
                    .toList();
        }
        public void validateCustomerAge(LocalDate dateOfBirth) {
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();

        if (age < 18) {
            throw new IllegalAgeException();
        }
    }

    }
