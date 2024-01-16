package org.example.exception;

public class IdAlreadyUsedException extends RuntimeException {

    public IdAlreadyUsedException(Long id) {
        super("Id "+id+" is already used");
    }

}
