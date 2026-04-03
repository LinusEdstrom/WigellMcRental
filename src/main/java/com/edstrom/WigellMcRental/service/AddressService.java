package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.AddressCreateDto;
import com.edstrom.WigellMcRental.dto.AddressDto;
import com.edstrom.WigellMcRental.exception.AddressNotFoundException;
import com.edstrom.WigellMcRental.exception.CustomerAndAddressNoMatchException;
import com.edstrom.WigellMcRental.mapper.AddressMapper;
import com.edstrom.WigellMcRental.modell.Address;
import com.edstrom.WigellMcRental.modell.Customer;
import com.edstrom.WigellMcRental.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @Transactional
    public AddressDto createAddress(AddressCreateDto dto) {
        Address entityAddress = AddressMapper.toAddressEntity(dto);
        Address savedAddress = addressRepository.save(entityAddress);
        return AddressMapper.toAddressDto(savedAddress);
    }

    @Transactional
    public void addressDelete(Long customerId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow (() -> new AddressNotFoundException(addressId));

        boolean matchCustomer = address.getCustomers().stream()
                .anyMatch(c -> c.getId().equals(customerId));

        if (!matchCustomer) {
            throw new CustomerAndAddressNoMatchException(customerId);
        }
        for (Customer c : address.getCustomers()) {
            c.setAddress(null);
            // Tar bort addressen helt. Alt är ju att bara ta bort den från den här Customern.
        }
        addressRepository.delete(address);
    }

}
