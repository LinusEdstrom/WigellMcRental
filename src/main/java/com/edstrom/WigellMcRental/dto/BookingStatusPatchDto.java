package com.edstrom.WigellMcRental.dto;

import com.edstrom.WigellMcRental.service.Status;

public record BookingStatusPatchDto(

        Status status
) {
}
