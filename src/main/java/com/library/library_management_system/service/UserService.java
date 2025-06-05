
package com.library.library_management_system.service;

import com.library.library_management_system.dto.mapper.UserMapper;
import com.library.library_management_system.dto.request.UserRequestDto;
import com.library.library_management_system.dto.response.UserResponseDto;
import com.library.library_management_system.exception.EmptyListException;
import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Add role checking after adding spring security
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> getAllUsers() {
//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("You are not allowed to access this resource");
//        }

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EmptyListException("User list is empty");
        }

        return users.stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(Long userId) {
//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("You are not allowed to access this resource");
//        }
        return userMapper
                .toUserResponseDto(userRepository
                        .findById(userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("User with id " + userId + " not found")));

    }

    public UserResponseDto getUserByEmail(String email) {
//        if (requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("You are not allowed to access this resource");
//        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User with email " + email + " not found");
        }

        return userMapper.toUserResponseDto(user);

    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
//        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
//            throw new EmailAlreadyRegisteredException("Email already registered");
//        }

        User user = userMapper.toEntity(requestDto);

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(savedUser);

    }

    public UserResponseDto updateMember(Long userId, UserRequestDto updateDto) {
//        if (!requestor.getId().equals(userId) && requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("You can only update your own account.");
//        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found"));

        user.setName(updateDto.getUsername());
        user.setEmail(updateDto.getEmail());

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(updatedUser);

    }

    public void deleteUser(Long userId) {
//        if (!requestor.getId().equals(userId) && requestor.getRole() != UserRole.ADMIN) {
//            throw new UnauthorizedAccessException("You can only delete your own account.");
//        }

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");

        }
        userRepository.deleteById(userId);
    }

}
