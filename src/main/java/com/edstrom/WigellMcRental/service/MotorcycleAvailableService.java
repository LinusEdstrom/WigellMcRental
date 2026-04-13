package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.MotorcycleAvailableDto;
import com.edstrom.WigellMcRental.mapper.MotorcycleAvailableMapper;
import com.edstrom.WigellMcRental.repository.McRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MotorcycleAvailableService {

    private final McRepository mcRepository;

    public MotorcycleAvailableService(McRepository mcRepository) {
        this.mcRepository = mcRepository;
    }

    @Transactional
    public List<MotorcycleAvailableDto> getAvailableMotorcycles(LocalDate from, LocalDate to) {
        // mcRepository already filters unavailable motorcycles
        return mcRepository.findAvailableMotorcycles(from, to, Status.ACTIVE).stream()
                .map(MotorcycleAvailableMapper::toDto)
                .toList();
    }
}
