package com.edstrom.WigellMcRental.repository;

import com.edstrom.WigellMcRental.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
