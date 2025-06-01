package com.library.library_management_system.entity;

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
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private String title;
    private String author;
    private String genre;
    private Date publish_date;

}
