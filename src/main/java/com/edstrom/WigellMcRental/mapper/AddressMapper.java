package com.edstrom.WigellMcRental.mapper;

import com.edstrom.WigellMcRental.dto.AddressCreateDto;
import com.edstrom.WigellMcRental.dto.AddressDto;
import com.edstrom.WigellMcRental.model.Address;

public final class AddressMapper {

    public static Address toAddressEntity(AddressCreateDto dto){

        return new Address(
                dto.street(),
                dto.postalCode(),
                dto.city()
        );
    }
    public static AddressDto toAddressDto(Address address){
        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }




}
