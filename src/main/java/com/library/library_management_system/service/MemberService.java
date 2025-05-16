
package com.library.library_management_system.service;


import com.library.library_management_system.CustomException.EmptyListException;
import com.library.library_management_system.CustomException.ResourceNotFoundException;
import com.library.library_management_system.CustomException.UnauthorizedAccessException;
import com.library.library_management_system.Enum.MemberRole;
import com.library.library_management_system.model.Member;
import com.library.library_management_system.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(Member requestor) {
        if (requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to view members");
        }

        List<Member> memberList = memberRepository.findAll();

        if (memberList.isEmpty()) {
            throw new EmptyListException("Library has no registered members");
        }

        return memberList;
    }

    public Member getMemberById(Long memberId, Member requestor) {
        if (requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to view members");
        }
        return memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member with id " + memberId + " not found"));
    }

    public Member getMemberByEmail(String email, Member requestor) {

        if (requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("Only admins are allowed to view members");
        }

        Member member = memberRepository.findByMemberEmail(email);
        if (member == null) {
            throw new ResourceNotFoundException("Member with email " + email + " not found");
        }

        return member;
    }

    public Member addMember(Member member) {

        if (memberRepository.findByMemberEmail(member.getMemberEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        return memberRepository.save(member);
    }

    public Member updateMember(Long memberId, Member updateMember, Member requestor) {

        if (!requestor.getMemberId().equals(memberId) && requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("You can only update your own account.");
        }

        return memberRepository.findById(memberId).map(member -> {
            member.setMemberName(updateMember.getMemberName());
            member.setMemberEmail(updateMember.getMemberEmail());
            return memberRepository.save(member);
        }).orElseThrow(() -> new ResourceNotFoundException("Member with id " + memberId + " not found"));

    }

    public void deleteMember(Long memberId, Member requestor) {

        if (!requestor.getMemberId().equals(memberId) && requestor.getMemberRole() != MemberRole.ADMIN) {
            throw new UnauthorizedAccessException("You can only delete your own account.");
        }

        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member with id " + memberId + " not found");
        }

        memberRepository.deleteById(memberId);
    }

}
