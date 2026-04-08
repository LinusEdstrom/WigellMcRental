package com.edstrom.WigellMcRental.mapper;

import com.edstrom.WigellMcRental.dto.McCreateDto;
import com.edstrom.WigellMcRental.dto.McDto;
import com.edstrom.WigellMcRental.dto.MotorcycleAvailableDto;
import com.edstrom.WigellMcRental.model.Mc;

public final class McMapper {

    private McMapper() {}

    public static McDto toDto(Mc mc) {
        return new McDto(
                mc.getId(),
                mc.getModel(),
                mc.getName(),
                mc.getYear(),
                mc.getPricePerDay()
        );
    }
    public static Mc toEntity(McCreateDto dto) {
        return new Mc(
                dto.model(),
                dto.name(),
                dto.year(),
                dto.pricePerDay()
        );
    }
}
