package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemberEmail(String memberEmail);
}
