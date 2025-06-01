package com.library.library_management_system.controller;

import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.MemberRepository;
import com.library.library_management_system.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<User> getAllMembers(@RequestParam Long memberId) {
        User requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return memberService.getAllMembers(requestor);
    }

    @GetMapping("/id/{id}")
    public User getMemberById(@PathVariable Long id) {
        User requestor = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return memberService.getMemberById(id, requestor);
    }

    @GetMapping("/email/{email}")
    public User getMemberByEmail(@PathVariable String email, @RequestParam Long memberId) {
        User requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return memberService.getMemberByEmail(email, requestor);
    }

    @PostMapping
    public User createMember(@RequestBody User user) {
        return memberService.addMember(user);
    }

    @PutMapping("/{id}")
    public User updateMember(@PathVariable Long id, @RequestBody User updatedUser, @RequestParam Long memberId) {
        User requestor = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        return memberService.updateMember(id, updatedUser, requestor);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id, @RequestParam Long memberId) {
        User requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        memberService.deleteMember(id, requestor);
    }


}
