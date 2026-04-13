package com.edstrom.WigellMcRental.mapper;

import com.edstrom.WigellMcRental.dto.MotorcycleAvailableDto;
import com.edstrom.WigellMcRental.model.Mc;

import java.math.BigDecimal;

public final class MotorcycleAvailableMapper {

    private MotorcycleAvailableMapper() {}

    public static MotorcycleAvailableDto toDto(Mc mc, BigDecimal priceGbp) {
        return new MotorcycleAvailableDto(
                mc.getModel(),
                mc.getName(),
                mc.getYear(),
                mc.getPricePerDay(),
                priceGbp
        );
    }
    public static MotorcycleAvailableDto toDto(Mc mc) {
        return new MotorcycleAvailableDto(
                mc.getModel(),
                mc.getName(),
                mc.getYear(),
                mc.getPricePerDay(),
                null
        );
    }

    public static Mc toEntity(MotorcycleAvailableDto dto) {
        return new Mc(
                dto.model(),
                dto.name(),
                dto.year(),
                dto.pricePerDay()
        );
    }
}
