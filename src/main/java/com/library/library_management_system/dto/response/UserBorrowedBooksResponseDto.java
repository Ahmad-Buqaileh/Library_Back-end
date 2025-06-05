package com.library.library_management_system.dto.response;

import java.util.List;

public record UserBorrowedBooksResponseDto(
        Long userId,
        String username,
        List<BookResponseDto> borrowedBooks
) {}
