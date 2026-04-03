package com.edstrom.WigellMcRental.mapper;

import com.edstrom.WigellMcRental.dto.AddressDto;
import com.edstrom.WigellMcRental.dto.CustomerCreateDto;
import com.edstrom.WigellMcRental.dto.CustomerDto;
import com.edstrom.WigellMcRental.dto.CustomerUpdateDto;
import com.edstrom.WigellMcRental.exception.AddressNotFoundException;
import com.edstrom.WigellMcRental.modell.Address;

import com.edstrom.WigellMcRental.modell.Customer;
import com.edstrom.WigellMcRental.repository.AddressRepository;



public final class CustomerMapper {

    private CustomerMapper(){}

    public static CustomerDto toCustomerDto(Customer c) {
        AddressDto addressDto = null;
        if(c.getAddress()!= null) {
        addressDto = new AddressDto(
                c.getAddress().getId(),
                c.getAddress().getStreet(),
                c.getAddress().getPostalCode(),
                c.getAddress().getCity()
        );
        }
        return new CustomerDto(
        c.getId(),
        c.getFirstName(),
        c.getLastName(),
        addressDto,
        c.getEmail(),
        c.getPhoneNumber(),
                c.getDateOfBirth()
        );
    }
    public static Customer toCustomerEntity(CustomerCreateDto dto,
                                            AddressRepository addressRepository){

        //Kör att man matar in addresser på Create tills vidare iaf
        Address address;
        if(dto.address().id() != null) {
            address = addressRepository.findById(dto.address().id())
                    .orElseThrow(() -> new AddressNotFoundException(dto.address().id()));
        }else {
            address = new Address(
                    dto.address().street(),
                    dto.address().postalCode(),
                    dto.address().city()
            );
            addressRepository.save(address);
        }
            // User och KEY osv borde ska in här. Hur mycket bök ska de bli sa flickan

            return new Customer(
                    dto.firstName(),
                    dto.lastName(),
                    address,
                    dto.email(),
                    dto.phoneNumber(),
                    dto.dateOfBirth()
            );
        }
        public static void applyUpdate(Customer customer, CustomerUpdateDto dto){

            customer.setFirstName(dto.firstName());
            customer.setLastName(dto.lastName());
            customer.setEmail(dto.email());
            customer.setPhoneNumber(dto.phoneNumber());
            customer.setDateOfBirth(dto.dateOfBirth());

            if (dto.phoneNumber() != null) {
                customer.setPhoneNumber(dto.phoneNumber());
            }

            if (dto.dateOfBirth() != null) {
                customer.setDateOfBirth(dto.dateOfBirth());
            }
        }

    }








