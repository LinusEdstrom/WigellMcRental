package com.edstrom.WigellMcRental.controller;



import com.edstrom.WigellMcRental.service.AdminService;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/v1")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }




}
