package com.library.library_management_system.controller;

import com.library.library_management_system.exception.ResourceNotFoundException;
import com.library.library_management_system.entity.Member;
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
    public List<Member> getAllMembers(@RequestParam Long memberId) {
        Member requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return memberService.getAllMembers(requestor);
    }

    @GetMapping("/id/{id}")
    public Member getMemberById(@PathVariable Long id) {
        Member requestor = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return memberService.getMemberById(id, requestor);
    }

    @GetMapping("/email/{email}")
    public Member getMemberByEmail(@PathVariable String email, @RequestParam Long memberId) {
        Member requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return memberService.getMemberByEmail(email, requestor);
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberService.addMember(member);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member updatedMember, @RequestParam Long memberId) {
        Member requestor = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        return memberService.updateMember(id, updatedMember, requestor);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id, @RequestParam Long memberId) {
        Member requestor = memberRepository.findById(memberId).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        memberService.deleteMember(id, requestor);
    }


}
