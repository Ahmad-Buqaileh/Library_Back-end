package com.library.library_management_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRequestDto {

    @NotNull
    private LocalDate borrowDate;

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;
}
