package com.library.library_management_system.exception;

public class BookIsNotBorrowedException extends RuntimeException {
    public BookIsNotBorrowedException(String message) {
        super(message);
    }
}
