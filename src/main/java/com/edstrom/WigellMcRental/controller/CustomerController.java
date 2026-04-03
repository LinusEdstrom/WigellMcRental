package com.edstrom.WigellMcRental.controller;

import com.edstrom.WigellMcRental.dto.*;
import com.edstrom.WigellMcRental.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping()
    public List<CustomerDto> listAllCustomers()
    {return customerService.findAllCustomers();}

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Long id)
    {return customerService.findByCustomerId(id);}

    @PostMapping()
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerCreateDto dto) {
        CustomerDto created = customerService.createCustomer(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id()).toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerUpdateDto dto){
        CustomerDto updateCustomer = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(updateCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer (@PathVariable Long id) {
        customerService.customerDelete(id);
        return ResponseEntity
                .noContent().build();
    }

}
