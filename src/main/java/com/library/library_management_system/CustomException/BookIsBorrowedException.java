package com.library.library_management_system.CustomException;

public class BookIsBorrowedException extends RuntimeException {
    public BookIsBorrowedException(String message) {
        super(message);
    }
}
