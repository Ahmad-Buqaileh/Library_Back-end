package com.library.library_management_system.repository;

import com.library.library_management_system.enums.BookStatus;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByMemberAndBook_BookStatus(Member member, BookStatus bookStatus);
}
