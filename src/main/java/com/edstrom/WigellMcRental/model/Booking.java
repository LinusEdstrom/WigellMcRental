package com.edstrom.WigellMcRental.model;

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
    @JoinTable( name = "booking_mc",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "mc_id")
    )
    private List<Mc> mcs;

    public Booking(){}

    public Booking(Customer customer, List<Mc> mcs, LocalDate rentalDate, LocalDate returnDate) {
        this.customer = customer;
        this.mcs = mcs;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<Mc> getMcs() { return mcs; }
    public void setMcs(List<Mc> mcs) { this.mcs = mcs; }

    public LocalDate getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    }


