package com.edstrom.WigellMcRental.repository;

import com.edstrom.WigellMcRental.model.Mc;
import com.edstrom.WigellMcRental.service.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface McRepository extends JpaRepository<Mc, Long> {

    @Query("""
    SELECT mc
    FROM Mc mc
    WHERE mc.id NOT IN (
        SELECT mc2.id
        FROM Booking b
        JOIN b.mcs mc2
        WHERE b.status = :status
        AND b.rentalDate <= :rentalEnd
        AND b.returnDate >= :rentalStart
    )
""")
    List<Mc> findAvailableMotorcycles(
            @Param("rentalStart") LocalDate rentalStart,
            @Param("rentalEnd") LocalDate rentalEnd,
            @Param("status") Status status
    );



}
