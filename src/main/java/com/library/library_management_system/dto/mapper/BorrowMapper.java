package com.library.library_management_system.dto.mapper;

import com.library.library_management_system.dto.request.BorrowRequestDto;
import com.library.library_management_system.dto.response.BorrowResponseDto;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.enums.BookStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BorrowMapper {

    private final BookMapper bookMapper;

    public BorrowMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public Borrow toEntity(BorrowRequestDto request, User user, Book book) {
        book.setStatus(BookStatus.BORROWED);
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(request.getBorrowDate());
        return borrow;
    }

    public BorrowResponseDto toResponseDto(Borrow borrow) {
        return new BorrowResponseDto(
                borrow.getId(),
                borrow.getBorrowDate(),
                List.of(bookMapper.toBookResponseDto(borrow.getBook()))
        );
    }
}

