package com.library.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.library.library_management_system.enums.BookStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private String title;
    private String author;
    private String description;
    private String genre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Borrow> borrows;


    @Column(name = "publish_date")
    private LocalDate publishDate;

}
