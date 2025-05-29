package com.library.library_management_system.controller;

import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.Member;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.MemberRepository;
import com.library.library_management_system.service.BorrowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrows")
public class BorrowController {

    private final BorrowService borrowService;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public BorrowController(BorrowService borrowService, MemberRepository memberRepository, BookRepository bookRepository) {
        this.borrowService = borrowService;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/member/{memberId}")
    public List<Borrow> getBorrowedBooksFromMember(@PathVariable Long memberId, @RequestParam Long requestorId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Member requestor = memberRepository.findById(requestorId)
                .orElseThrow(() -> new ResourceNotFoundException("Requestor not found"));

        return borrowService.getAllBorrowedBooksFromMember(requestor, member);
    }

    @PostMapping
    public Borrow borrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return borrowService.bookBorrow(member, book);
    }

    @DeleteMapping("/{id}")
    public void returnBook(@PathVariable Long id, @RequestParam Long memberId, @RequestParam Long bookId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        borrowService.returnBook(id, member, book);
    }
}
