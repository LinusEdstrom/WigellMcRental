package com.edstrom.WigellMcRental.modell;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate rentalDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable( name = "mc",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "mc_id")
    )
    private List<Mc> mcs;

    protected Booking(){}

    public Booking(LocalDate rentalDate, LocalDate returnDate){
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }


    // Här ska kopplingen Customer in som ligger som en bokning med en eller flera mc under en viss tid
    //Varje member borde vara kopplad en member = en booking och inkludera valfritt antal mc:s

}
