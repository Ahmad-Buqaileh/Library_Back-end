package com.library.library_management_system.repository;

import com.library.library_management_system.Enum.BookStatus;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Borrow;
import com.library.library_management_system.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByMemberAndBook_BookStatus(Member member, BookStatus bookStatus);
}
