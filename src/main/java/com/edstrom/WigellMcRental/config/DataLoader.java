package com.edstrom.WigellMcRental.config;

import com.edstrom.WigellMcRental.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataLoader {

    private final AdminService adminService;

    public DataLoader(AdminService adminService){
        this.adminService = adminService;
    }
    @Bean
    CommandLineRunner loadData() {
        return args -> {
            adminService.createSampleData();


        };

    }


}
