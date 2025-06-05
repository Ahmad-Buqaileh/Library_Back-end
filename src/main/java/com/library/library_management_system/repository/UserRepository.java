package com.library.library_management_system.repository;

import com.library.library_management_system.dto.response.UserResponseDto;
import com.library.library_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    UserResponseDto findUserById(Long id);
}
