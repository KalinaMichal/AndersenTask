package org.example.exception;

public class FakeFatherException extends RuntimeException {

    public FakeFatherException() {
        super("Father with this id doesn't exist");
    }
}
