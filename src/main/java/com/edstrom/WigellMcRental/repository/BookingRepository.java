package com.edstrom.WigellMcRental.repository;

import com.edstrom.WigellMcRental.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findAll();



    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
        FROM Booking b
        JOIN b.mcs mc
        WHERE mc.id = :mcId
          AND NOT (:rentalEnd < b.rentalDate OR :rentalStart > b.returnDate)
    """)
    boolean existsBookingForMcBetween(@Param("mcId") Long mcId,
                                      @Param("rentalStart") LocalDate rentalStart,
                                      @Param("rentalEnd") LocalDate rentalEnd);


    //Patch måste excludera sin egen booking

    @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Booking b
    JOIN b.mcs mc
    WHERE mc.id = :mcId
      AND b.id <> :bookingId
      AND NOT (:rentalEnd < b.rentalDate OR :rentalStart > b.returnDate)
""")
    boolean existsBookingForMcBetweenExcludingBooking(
            @Param("mcId") Long mcId,
            @Param("rentalStart") LocalDate rentalStart,
            @Param("rentalEnd") LocalDate rentalEnd,
            @Param("bookingId") Long bookingId
    );
    @Modifying
    @Query("""
    UPDATE Booking b
    SET b.status = com.edstrom.WigellMcRental.service.Status.COMPLETED
    WHERE b.status = com.edstrom.WigellMcRental.service.Status.ACTIVE
    AND b.returnDate < :today
    """)
    int completeOldBookings(@Param("today") LocalDate today);


    List<Booking> findByCustomer_Id(Long customerId);

}
