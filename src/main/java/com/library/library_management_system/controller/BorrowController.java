package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.BorrowRequestDto;
import com.library.library_management_system.dto.response.BookResponseDto;
import com.library.library_management_system.dto.response.BorrowResponseDto;
import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.UserRepository;
import com.library.library_management_system.service.BorrowService;
import com.library.library_management_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService, UserService userService) {
        this.borrowService = borrowService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<BorrowResponseDto>> getBooksBorrowedByUser(@PathVariable Long id) {
        List<BorrowResponseDto> response = borrowService.getBooksBorrowedByUser(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BorrowResponseDto> createBorrow(@Valid @RequestBody BorrowRequestDto borrowRequestDto) {
        BorrowResponseDto response = borrowService.borrowBook(borrowRequestDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(@Valid @RequestBody BorrowRequestDto requestDto) {
        borrowService.returnBook(requestDto);
    }

}
