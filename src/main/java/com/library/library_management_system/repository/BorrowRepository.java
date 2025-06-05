package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Book;
import com.library.library_management_system.enums.BookStatus;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUserId(Long id);
    Optional<Borrow> findByUserAndBook(User user, Book book);
}
