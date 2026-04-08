package com.edstrom.WigellMcRental.controller;

import com.edstrom.WigellMcRental.dto.MotorcycleAvailableDto;
import com.edstrom.WigellMcRental.service.McService;
import com.edstrom.WigellMcRental.service.MotorcycleAvailableService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MotorcycleAvailabilityController {

    private final MotorcycleAvailableService motorcycleAvailableService;

    public MotorcycleAvailabilityController(MotorcycleAvailableService motorcycleAvailableService) {
        this.motorcycleAvailableService = motorcycleAvailableService;
    }

    @GetMapping("/availability")
    public ResponseEntity<List<MotorcycleAvailableDto>> getAvailableMotorcycles(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        List<MotorcycleAvailableDto> available = motorcycleAvailableService.getAvailableMotorcycles(from, to);
        return ResponseEntity.ok(available);
    }
}
