package com.library.library_management_system.entity;

import com.library.library_management_system.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Table(name = "members")
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String memberName;
    private String memberEmail;
    private LocalDate memberShipDate;
}
