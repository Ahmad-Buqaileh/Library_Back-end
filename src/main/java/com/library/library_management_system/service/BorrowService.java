package com.library.library_management_system.service;


import com.library.library_management_system.exception.BookIsBorrowedException;
import com.library.library_management_system.exception.BookIsNotBorrowedException;
import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.exception.UnauthorizedAccessException;
import com.library.library_management_system.enums.BookStatus;
import com.library.library_management_system.enums.MemberRole;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Borrow;
import com.library.library_management_system.model.Member;
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

    public List<Book> getAllBorrowedBooksFromLibrary(Member requestor) {

        if (requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins can see borrowed books");
        }

        return bookRepository.findByBookStatus(BookStatus.BORROWED);
    }

    public List<Borrow> getAllBorrowedBooksFromMember(Member requestor, Member member) {

        if (!requestor.getMemberId().equals(member.getMemberId()) && requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("You can only see your borrowed books");
        }
        return  borrowRepository.findByMemberAndBook_BookStatus(member, BookStatus.BORROWED);
    }

    public Borrow bookBorrow(Member member, Book book) {
        if (book.getBookStatus() != BookStatus.AVAILABLE) {
            throw new BookIsBorrowedException("Book is already borrowed");
        }

        book.setBookStatus(BookStatus.BORROWED);
        bookRepository.save(book);

        Borrow borrow = new Borrow();
        borrow.setMember(member);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());

        return borrowRepository.save(borrow);

    }

    public void returnBook(Long borrowId, Member member, Book book) {

        if (book.getBookStatus() != BookStatus.BORROWED) {
            throw new BookIsNotBorrowedException("Book is not currently borrowed.");
        }

        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found."));

        if (!borrow.getMember().getMemberId().equals(member.getMemberId())) {
            throw new UnauthorizedAccessException("You can only return books you have borrowed.");
        }

        book.setBookStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        borrowRepository.deleteById(borrowId);

    }

}
