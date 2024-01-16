package org.example.exception;

public class FakeMotherException extends RuntimeException {

    public FakeMotherException() {
        super("Mother with this id doesn't exist");
    }
}
