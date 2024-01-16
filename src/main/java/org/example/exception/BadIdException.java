package org.example.exception;

public class BadIdException extends RuntimeException {

    public BadIdException(Long id) {
        super("Id "+id+" doesn't exist");
    }
}
