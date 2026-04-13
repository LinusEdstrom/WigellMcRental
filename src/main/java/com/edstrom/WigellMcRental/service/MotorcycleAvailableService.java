package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.MotorcycleAvailableDto;
import com.edstrom.WigellMcRental.mapper.MotorcycleAvailableMapper;
import com.edstrom.WigellMcRental.repository.McRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MotorcycleAvailableService {

        private final McRepository mcRepository;
        private final PricingService pricingService;

        public MotorcycleAvailableService(McRepository mcRepository, PricingService pricingService) {
            this.mcRepository = mcRepository;
            this.pricingService = pricingService;
        }

        @Transactional(readOnly = true)
        public List<MotorcycleAvailableDto> getAvailableMotorcycles(LocalDate from, LocalDate to) {

            BigDecimal rate = pricingService.getRate("SEK", "GBP");

            return mcRepository.findAvailableMotorcycles(from, to, Status.ACTIVE).stream()
                    .map(mc -> {
                        BigDecimal priceGbp = mc.getPricePerDay().multiply(rate);
                        return MotorcycleAvailableMapper.toDto(mc, priceGbp);
                    })
                    .toList();
        }
    }

