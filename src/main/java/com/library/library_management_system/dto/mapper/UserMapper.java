package com.library.library_management_system.dto.mapper;

import com.library.library_management_system.dto.request.UserRequestDto;
import com.library.library_management_system.dto.response.BookResponseDto;
import com.library.library_management_system.dto.response.UserBorrowedBooksResponseDto;
import com.library.library_management_system.dto.response.UserResponseDto;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final BookMapper bookMapper;

    public UserMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public User toEntity(UserRequestDto userRequest) {

        User user = new User();
        user.setName(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setRole(UserRole.USER);
        return user;
    }

    public UserResponseDto toUserResponseDto(User user) {

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistrationDate()
        );
    }

    public UserBorrowedBooksResponseDto toBorrowedBooksResponse(User user) {
        List<BookResponseDto> borrowedBooks = user.getBorrows().stream()
                .map(Borrow::getBook)
                .map(bookMapper::toBookResponseDto)
                .toList();

        return new UserBorrowedBooksResponseDto(
                user.getId(),
                user.getName(),
                borrowedBooks
        );
    }


}
