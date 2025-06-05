package com.library.library_management_system.service;

import com.library.library_management_system.dto.mapper.BookMapper;
import com.library.library_management_system.dto.request.BookRequestDto;
import com.library.library_management_system.dto.response.BookResponseDto;
import com.library.library_management_system.exception.CantDeleteBorrwedBookException;
import com.library.library_management_system.exception.EmptyListException;
import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.enums.BookStatus;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Add role checking after adding spring security
 */

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            throw new EmptyListException("Library has no books!");
        }

        return books.stream()
                .map(bookMapper::toBookResponseDto)
                .toList();
    }

    public BookResponseDto getBookByID(Long bookId) {

        return bookMapper
                .toBookResponseDto(bookRepository
                        .findById(bookId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Book not found!")));

    }

    public BookResponseDto addBook(BookRequestDto requestDto) {
//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("Only admins are allowed to add a book to the library!");
//        }

        Book book = bookMapper.toEntity(requestDto);

        Book savedBook = bookRepository.save(book);

        return bookMapper.toBookResponseDto(savedBook);
    }

    public BookResponseDto updateBook(Long bookId, BookRequestDto requestDto) {

//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("Only admins are allowed to update a book to the library!");
//        }

        Book book = bookRepository
                .findById(bookId).orElseThrow(
                        () -> new ResourceNotFoundException("Book not found!"));

        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setGenre(requestDto.getGenre());
        book.setDescription(requestDto.getDescription());

        Book savedBook = bookRepository.save(book);

        return bookMapper.toBookResponseDto(savedBook);

    }

    public void deleteBook(Long bookId) {

//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("Only admins are allowed to delete a book from the library!");
//        }

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book with ID " + bookId + " not found!"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new CantDeleteBorrwedBookException("Book with ID " + bookId + " is Borrowed!");
        }

        bookRepository.deleteById(bookId);
    }
}
