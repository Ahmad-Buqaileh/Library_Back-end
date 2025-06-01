package com.library.library_management_system.entity;

import com.library.library_management_system.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String name;
    private String email;
    private LocalDate registration_date;
}
