package com.edstrom.WigellMcRental.model;

import com.edstrom.WigellMcRental.service.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
    // Ska få in status här
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private BigDecimal totalPriceGbp;

    public Booking(){}

    public Booking(Customer customer, List<Mc> mcs, LocalDate rentalDate, LocalDate returnDate,
                   BigDecimal totalPrice, BigDecimal totalPriceGbp) {
        this.customer = customer;
        this.mcs = mcs;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.totalPriceGbp = totalPriceGbp;

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

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPriceGbp() {
        return totalPriceGbp;
    }

    public void setTotalPriceGbp(BigDecimal totalPriceGbp) {
        this.totalPriceGbp = totalPriceGbp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}



