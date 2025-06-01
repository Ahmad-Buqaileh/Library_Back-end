
package com.library.library_management_system.service;


import com.library.library_management_system.exception.EmptyListException;
import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.exception.UnauthorizedAccessException;
import com.library.library_management_system.enums.MemberRole;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<User> getAllMembers(User requestor) {
        if (requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to view members");
        }

        List<User> userList = memberRepository.findAll();

        if (userList.isEmpty()) {
            throw new EmptyListException("Library has no registered members");
        }

        return userList;
    }

    public User getMemberById(Long memberId, User requestor) {
        if (requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to view members");
        }
        return memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member with id " + memberId + " not found"));
    }

    public User getMemberByEmail(String email, User requestor) {

        if (requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to view members");
        }

        User user = memberRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Member with email " + email + " not found");
        }

        return user;
    }

    public User addMember(User user) {

        if (memberRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        return memberRepository.save(user);
    }

    public User updateMember(Long memberId, User updateUser, User requestor) {

        if (!requestor.getId().equals(memberId) && requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("You can only update your own account.");
        }

        return memberRepository.findById(memberId).map(member -> {
            member.setName(updateUser.getName());
            member.setEmail(updateUser.getEmail());
            return memberRepository.save(member);
        }).orElseThrow(() -> new ResourceNotFoundException("Member with id " + memberId + " not found"));

    }

    public void deleteMember(Long memberId, User requestor) {

        if (!requestor.getId().equals(memberId) && requestor.getRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("You can only delete your own account.");
        }

        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member with id " + memberId + " not found");
        }

        memberRepository.deleteById(memberId);
    }

}
