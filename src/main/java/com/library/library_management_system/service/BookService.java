package com.library.library_management_system.service;

import com.library.library_management_system.CustomException.CantDeleteBorrwedBookException;
import com.library.library_management_system.CustomException.EmptyListException;
import com.library.library_management_system.CustomException.ResourceNotFoundException;
import com.library.library_management_system.CustomException.UnauthorizedAccessException;
import com.library.library_management_system.Enum.BookStatus;
import com.library.library_management_system.Enum.MemberRole;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Member;
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

    public Book addBook(Book book, Member requestor) {

        if (requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to add a book to the library!");
        }

        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, Book updatedbook, Member requestor) {

        if (requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to update a book to the library!");
        }

        return bookRepository.findById(bookId).map(book -> {
            book.setBookTitle(updatedbook.getBookTitle());
            book.setBookAuthor(updatedbook.getBookAuthor());
            book.setPublishedDate(updatedbook.getPublishedDate());
            book.setBookGenre(updatedbook.getBookGenre());
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Book with ID " + bookId + " not found!"));
    }

    public void deleteBook(Long bookId, Member requestor) {

        if (requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to delete a book from the library!");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book with ID " + bookId + " not found!"));

        if (book.getBookStatus() != BookStatus.AVAILABLE) {
            throw new CantDeleteBorrwedBookException("Book with ID " + bookId + " is Borrowed!");
        }

        bookRepository.deleteById(bookId);
    }
}
