package com.library.library_management_system.dto.mapper;

import com.library.library_management_system.dto.request.BookRequestDto;
import com.library.library_management_system.dto.response.BookResponseDto;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.enums.BookStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book toEntity(BookRequestDto bookRequest) {

        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription());
        book.setGenre(bookRequest.getGenre());
        book.setPublishDate(bookRequest.getPublishDate());
        book.setStatus(BookStatus.AVAILABLE);

        return book;
    }

    public BookResponseDto toBookResponseDto(Book book) {

        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getGenre(),
                book.getPublishDate()
        );
    }

    public List<BookResponseDto> toBookResponseDtoList(List<Book> books) {
        return books.stream()
                .map(this::toBookResponseDto)
                .collect(Collectors.toList());
    }


}
