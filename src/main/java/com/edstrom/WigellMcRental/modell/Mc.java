package com.edstrom.WigellMcRental.modell;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mc")
public class Mc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String modell;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String year;

    @Column(nullable = false)
    private Long pricePerDay;

    @Column(nullable = false)
    private boolean isAvailable;

    // Här ska det in att Mc kopplas med booking och att isAvailable ändras till false
    @ManyToMany(mappedBy = "mcs")
    private List<Booking> bookings;

    protected Mc(){}

    public Mc(String modell, String name, String year, Long pricePerDay, boolean isAvailable){
        this.modell = modell;
        this.name = name;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.isAvailable = isAvailable;
    }

}
