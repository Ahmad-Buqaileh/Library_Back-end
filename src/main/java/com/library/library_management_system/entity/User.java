package com.library.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.library.library_management_system.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String name;
    private String password;
    private String email;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Borrow> borrows;


    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDate.now();
    }
}
