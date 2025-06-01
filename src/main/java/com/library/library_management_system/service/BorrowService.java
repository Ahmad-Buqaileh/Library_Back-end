package com.library.library_management_system.service;


import com.library.library_management_system.exception.BookIsBorrowedException;
import com.library.library_management_system.exception.BookIsNotBorrowedException;
import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.exception.UnauthorizedAccessException;
import com.library.library_management_system.enums.BookStatus;
import com.library.library_management_system.enums.MemberRole;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final BorrowRepository bookBorrowRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository, BorrowRepository bookBorrowRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.bookBorrowRepository = bookBorrowRepository;
    }

    public List<Book> getAllBorrowedBooksFromLibrary(User requestor) {

        if (requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins can see borrowed books");
        }

        return bookRepository.findByStatus(BookStatus.BORROWED);
    }

    public List<Borrow> getAllBorrowedBooksFromMember(User requestor, User user) {

        if (!requestor.getId().equals(user.getId()) && requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("You can only see your borrowed books");
        }
        return  borrowRepository.findByUserAndBook_status(user, BookStatus.BORROWED);
    }

    public Borrow bookBorrow(User user, Book book) {
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BookIsBorrowedException("Book is already borrowed");
        }

        book.setStatus(BookStatus.BORROWED);
        bookRepository.save(book);

        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrow_date(LocalDate.now());

        return borrowRepository.save(borrow);

    }

    public void returnBook(Long borrowId, User user, Book book) {

        if (book.getStatus() != BookStatus.BORROWED) {
            throw new BookIsNotBorrowedException("Book is not currently borrowed.");
        }

        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found."));

        if (!borrow.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You can only return books you have borrowed.");
        }

        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        borrowRepository.deleteById(borrowId);

    }

}
