package com.edstrom.WigellMcRental.controller;


import com.edstrom.WigellMcRental.dto.AddressCreateDto;
import com.edstrom.WigellMcRental.dto.AddressDto;
import com.edstrom.WigellMcRental.dto.CustomerCreateDto;
import com.edstrom.WigellMcRental.dto.CustomerDto;
import com.edstrom.WigellMcRental.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }
    @GetMapping("/customers")
    public List <CustomerDto> listAllCustomers()
    {return adminService.findAllCustomers();}

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable Long id)
    {return adminService.findByCustomerId(id);}

    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerCreateDto dto) {
        CustomerDto created = adminService.createCustomer(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id()).toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }
    @PostMapping("/customers/{customerId}/addresses")
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid AddressCreateDto dto){
        AddressDto created = adminService.createAddress(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{addressId}")
                .buildAndExpand(created.id()).toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }
    @PutMapping("customers/{customerId}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateDto dto) {
        return adminService.customerUpdate(id, dto);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer (@PathVariable Long id) {
        adminService.customerDelete(id);
        return ResponseEntity
                .noContent().build();
    }
    @DeleteMapping("customers/{customerId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress (
            @PathVariable Long customerId,
            @PathVariable Long addressId) {
        adminService.addressDelete(customerId, addressId);
        return ResponseEntity
                .noContent().build();
    }



}
