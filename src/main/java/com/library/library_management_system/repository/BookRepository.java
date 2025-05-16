package com.library.library_management_system.repository;

import com.library.library_management_system.Enum.BookStatus;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookStatus(BookStatus bookStatus);
}
