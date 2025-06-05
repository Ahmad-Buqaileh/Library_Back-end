package com.library.library_management_system.dto.response;

import java.time.LocalDate;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        String description,
        String genre,
        LocalDate publishDate
) {
}
