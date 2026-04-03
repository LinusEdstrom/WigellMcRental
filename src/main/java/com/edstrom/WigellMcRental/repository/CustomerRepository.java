package com.edstrom.WigellMcRental.repository;

import com.edstrom.WigellMcRental.modell.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);
}
