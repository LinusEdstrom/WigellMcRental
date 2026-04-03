package com.edstrom.WigellMcRental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public record CustomerUpdateDto(

        @NotBlank @Size(min = 2, max = 20) String firstName,
        @NotBlank @Size(min = 2, max = 20) String lastName,
        @NotBlank @Email @Size(max = 50) String email,
        @Size(max = 20) String phoneNumber,
        LocalDate dateOfBirth
) {
}
