package com.edstrom.WigellMcRental.repository;

import com.edstrom.WigellMcRental.modell.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
