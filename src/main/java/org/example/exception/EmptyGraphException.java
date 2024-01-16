package org.example.exception;

public class EmptyGraphException extends RuntimeException {

    public EmptyGraphException() {
        super("Graph is empty");
    }
}