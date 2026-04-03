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




}
