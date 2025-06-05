package com.library.library_management_system.dto.response;


import java.time.LocalDate;
import java.util.List;

public record BorrowResponseDto(
        Long id,
        LocalDate borrowDate,
        List<BookResponseDto> borrowedBooks
) {
}
