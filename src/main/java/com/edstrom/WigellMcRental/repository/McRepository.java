package com.edstrom.WigellMcRental.repository;

import com.edstrom.WigellMcRental.model.Mc;
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
           WHERE NOT (:rentalEnd < b.rentalDate OR :rentalStart > b.returnDate)
        )
    """)
    List<Mc> findAvailableMotorcycles(@Param("rentalStart") LocalDate rentalStart,
                                      @Param("rentalEnd") LocalDate rentalEnd);




}
