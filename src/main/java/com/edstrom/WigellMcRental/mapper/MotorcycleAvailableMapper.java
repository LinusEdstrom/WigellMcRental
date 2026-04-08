package com.edstrom.WigellMcRental.mapper;

import com.edstrom.WigellMcRental.dto.MotorcycleAvailableDto;
import com.edstrom.WigellMcRental.model.Mc;

public final class MotorcycleAvailableMapper {

    private MotorcycleAvailableMapper() {}

    public static MotorcycleAvailableDto toDto(Mc mc) {
        return new MotorcycleAvailableDto(
                mc.getModel(),
                mc.getName(),
                mc.getYear(),
                mc.getPricePerDay()
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
