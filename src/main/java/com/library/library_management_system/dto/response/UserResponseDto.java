package com.library.library_management_system.dto.response;

import com.library.library_management_system.enums.UserRole;

import java.time.LocalDate;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        LocalDate registrationDate
) {
}
