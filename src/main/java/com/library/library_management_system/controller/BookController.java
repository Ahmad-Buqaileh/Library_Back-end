package com.library.library_management_system.controller;

import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Member;
import com.library.library_management_system.repository.MemberRepository;
import com.library.library_management_system.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final MemberRepository memberRepository;

    public BookController(BookService bookService, MemberRepository memberRepository) {
        this.bookService = bookService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookByID(id);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book, @RequestParam Long memberId) {
        Member requestor = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        return bookService.addBook(book, requestor);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book, @RequestParam Long memberId) {
        Member requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return bookService.updateBook(id, book, requestor);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id, @RequestParam Long memberId) {
        Member requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        bookService.deleteBook(id, requestor);
    }


}