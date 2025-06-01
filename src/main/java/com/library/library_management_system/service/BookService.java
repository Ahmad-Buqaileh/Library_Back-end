package com.library.library_management_system.service;

import com.library.library_management_system.exception.CantDeleteBorrwedBookException;
import com.library.library_management_system.exception.EmptyListException;
import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.exception.UnauthorizedAccessException;
import com.library.library_management_system.enums.BookStatus;
import com.library.library_management_system.enums.MemberRole;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        List<Book> booksList = bookRepository.findAll();

        if (booksList.isEmpty()) {
            throw new EmptyListException("Library has no books!");
        }

        return booksList;
    }

    public Book getBookByID(Long bookId) {

        return bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book with ID " + bookId + " not found!"));

    }

    public Book addBook(Book book, User requestor) {

        if (requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to add a book to the library!");
        }

        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, Book updatedbook, User requestor) {

        if (requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to update a book to the library!");
        }

        return bookRepository.findById(bookId).map(book -> {
            book.setTitle(updatedbook.getTitle());
            book.setAuthor(updatedbook.getAuthor());
            book.setPublish_date(updatedbook.getPublish_date());
            book.setGenre(updatedbook.getGenre());
            return bookRepository.save( book);
        }).orElseThrow(() -> new ResourceNotFoundException("Book with ID " + bookId + " not found!"));
    }

    public void deleteBook(Long bookId, User requestor) {

        if (requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to delete a book from the library!");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book with ID " + bookId + " not found!"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new CantDeleteBorrwedBookException("Book with ID " + bookId + " is Borrowed!");
        }

        bookRepository.deleteById(bookId);
    }
}
