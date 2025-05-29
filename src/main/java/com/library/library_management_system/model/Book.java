package com.library.library_management_system.model;

import com.library.library_management_system.enums.BookStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private String bookTitle;
    private String bookAuthor;
    private Date publishedDate;
    private String bookGenre;

}
