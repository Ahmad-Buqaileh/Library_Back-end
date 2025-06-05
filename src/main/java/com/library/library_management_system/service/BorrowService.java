package com.library.library_management_system.service;


import com.library.library_management_system.dto.mapper.BookMapper;
import com.library.library_management_system.dto.mapper.BorrowMapper;
import com.library.library_management_system.dto.request.BorrowRequestDto;
import com.library.library_management_system.dto.response.BookResponseDto;
import com.library.library_management_system.dto.response.BorrowResponseDto;
import com.library.library_management_system.dto.response.UserResponseDto;
import com.library.library_management_system.exception.*;
import com.library.library_management_system.enums.BookStatus;
import com.library.library_management_system.enums.UserRole;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.BorrowRepository;
import com.library.library_management_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final BorrowMapper borrowMapper;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository, BorrowMapper borrowMapper, BookMapper bookMapper, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.borrowMapper = borrowMapper;
        this.bookMapper = bookMapper;
        this.userRepository = userRepository;
    }

    public List<BookResponseDto> getAllBorrowedBooksFromLibrary() {
//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("Only admins can see borrowed books");
//       }
        List<Book> books = bookRepository.findByStatus(BookStatus.BORROWED);

        if (books.isEmpty()) {
            throw new EmptyListException("No Borrowed Books found");
        }

        return books.stream()
                .map(bookMapper::toBookResponseDto)
                .toList();
    }

    public List<BookResponseDto> getAllAvailableBooksFromLibrary() {
//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("Only admins can see borrowed books");
//       }
        List<Book> books = bookRepository.findByStatus(BookStatus.AVAILABLE);

        if (books.isEmpty()) {
            throw new EmptyListException("No Borrowed Books found");
        }

        return books.stream()
                .map(bookMapper::toBookResponseDto)
                .toList();
    }

    public List<BorrowResponseDto> getBooksBorrowedByUser(Long userId) {
//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("Only admins can see borrowed books");
//       }

        List<Borrow> borrows = borrowRepository.findByUserId(userId);

        if (borrows.isEmpty()) {
            throw new EmptyListException("No Borrowed Books found");
        }

        return borrows.stream()
                .map(borrowMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public BorrowResponseDto borrowBook(BorrowRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + requestDto.getUserId() + " not found"));

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Book with id " + requestDto.getBookId() + " not found"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BookIsBorrowedException("Book is not available");
        }

        Borrow borrow = borrowMapper.toEntity(requestDto, user, book);

        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());

        book.setStatus(BookStatus.BORROWED);
        Borrow savedBorrowed = borrowRepository.save(borrow);

        return borrowMapper.toResponseDto(savedBorrowed);
    }

    @Transactional
    public void returnBook(BorrowRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + requestDto.getUserId() + " not found"));

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + requestDto.getBookId() + " not found"));

        if (book.getStatus() != BookStatus.BORROWED) {
            throw new BookIsNotBorrowedException("Book is not currently borrowed.");
        }

        Borrow borrow = borrowRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new ResourceNotFoundException("No matching borrow record found."));

        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        borrowRepository.delete(borrow);
    }

}
