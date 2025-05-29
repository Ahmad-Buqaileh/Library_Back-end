package com.library.library_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Table(name = "borrow_book")
@Data
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;

    private LocalDate borrowDate;

    @ManyToOne
    @JoinColumn(name = "member_id_fk")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id_fk")
    private Book book;
}
