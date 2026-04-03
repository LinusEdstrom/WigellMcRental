package com.edstrom.WigellMcRental.dto;

import java.time.LocalDate;

public record CustomerDto(
        Long id,
        String firstName,
        String lastName,
        AddressDto address,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth

) {
}
