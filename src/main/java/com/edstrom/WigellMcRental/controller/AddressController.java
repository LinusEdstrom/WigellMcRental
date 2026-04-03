package com.edstrom.WigellMcRental.controller;

import com.edstrom.WigellMcRental.dto.AddressCreateDto;
import com.edstrom.WigellMcRental.dto.AddressDto;
import com.edstrom.WigellMcRental.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }


    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress (
            @PathVariable Long customerId,
            @PathVariable Long addressId) {
        addressService.addressDelete(customerId, addressId);
        return ResponseEntity
                .noContent().build();
    }

    @PostMapping()
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid AddressCreateDto dto){
        AddressDto created = addressService.createAddress(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{addressId}")
                .buildAndExpand(created.id()).toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }
}
