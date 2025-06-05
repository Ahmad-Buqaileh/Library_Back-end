package com.library.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "borrows")
@Data
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @ManyToOne
    @JoinColumn(name = "member_id_fk")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id_fk")
    @JsonBackReference
    private Book book;



}
