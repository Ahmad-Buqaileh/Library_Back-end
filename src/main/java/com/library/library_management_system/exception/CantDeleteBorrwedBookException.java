package com.library.library_management_system.exception;

public class CantDeleteBorrwedBookException extends RuntimeException {
    public CantDeleteBorrwedBookException(String message) {
        super(message);
    }
}
