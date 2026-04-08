package com.edstrom.WigellMcRental.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mcs")
public class Mc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String model;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String year;

    @Column(nullable = false)
    private Long pricePerDay;

    // Här ska det in att Mc kopplas med booking och att isAvailable ändras till false
    @ManyToMany(mappedBy = "mcs")
    private List<Booking> bookings;

    protected Mc(){}

    public Mc(String model, String name, String year, Long pricePerDay){
        this.model = model;
        this.name = name;
        this.year = year;
        this.pricePerDay = pricePerDay;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Long pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
