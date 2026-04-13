package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.*;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
    public List<BookingDto> findAll(){
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toDto)
                        .toList();
        }
    @Transactional(readOnly = true)
    public BookingDto findById(Long id){
        return bookingRepository.findById(id)
                .map(BookingMapper::toDto)
                .orElseThrow (() ->new BookingNotFoundException(id));
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

        bookingEntity.setStatus(Status.ACTIVE);

        BigDecimal totalPrice = calculateTotalPrice(
                motorcycles,
                dto.rentalDate(),
                dto.returnDate()
        );
        bookingEntity.setTotalPrice(totalPrice);
        Booking savedBooking = bookingRepository.save(bookingEntity);
        return BookingMapper.toDto(savedBooking);
    }
    public BookingDto completeBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        booking.setStatus(Status.COMPLETED);

        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    /*@Transactional
    public BookingDto patchBooking(Long bookingId, BookingPatchDto dto, Authentication auth) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        String role = auth.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {

            booking.setStatus(Status.INACTIVE);

            return BookingMapper.toDto(bookingRepository.save(booking));
        }

        LocalDate newStart = dto.rentalDate() != null
                ? dto.rentalDate()
                : booking.getRentalDate();

        LocalDate newEnd = dto.returnDate() != null
                ? dto.returnDate()
                : booking.getReturnDate();

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

        booking.setMcs(newMotorcycles);
        booking.setRentalDate(newStart);
        booking.setReturnDate(newEnd);

        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    @Transactional
    public BookingDto patchBookingStatus(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        booking.setStatus(Status.INACTIVE);

        return BookingMapper.toDto(booking);
    }

     */

        @Transactional
        public void deleteById(Long id) {
        if (!bookingRepository.existsById(id)){
            throw new BookingNotFoundException(id);
        }
        bookingRepository.deleteById(id);
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
            List<Mc> available = mcRepository.findAvailableMotorcycles(from, to, Status.ACTIVE);

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
    private BigDecimal calculateTotalPrice(List<Mc> motorcycles,
                                           LocalDate start,
                                           LocalDate end) {

        long days = ChronoUnit.DAYS.between(start, end) + 1;

        return motorcycles.stream()
                .map(Mc::getPricePerDay)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(BigDecimal.valueOf(days));
    }
    // Den här va ju lite cool, sätter sec, min, hour, day, month dayofweek
    //Kollar repo efter bokningar som ska vara COMPLETED
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateCompletedBookings() {
        bookingRepository.completeOldBookings(LocalDate.now());
    }

    }
