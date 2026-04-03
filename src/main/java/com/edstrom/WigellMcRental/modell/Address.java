package com.edstrom.WigellMcRental.modell;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String street;

    @Column(nullable = false, length = 20)
    private String postalCode;

    @Column(nullable = false, length = 20)
    private String city;

    //Här ska in connection med customer, men tror man borde göra tillbaka kaka o
    //låta address äga connection så man slipper få med address varje customer Get
    @OneToMany(mappedBy = "address")
    @JsonIgnore     //för att undvika infinite loop men känns som att det vore naiz att sätta
    // innan Address address i customer för att inte få med hela addressen varje gång
    private List<Customer> customers;


    protected Address(){}

    public Address(String street, String postalCode, String city){
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
