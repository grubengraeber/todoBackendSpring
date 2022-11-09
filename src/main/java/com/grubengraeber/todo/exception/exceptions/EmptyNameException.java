package com.grubengraeber.todo.exception.exceptions;

public class EmptyNameException extends RuntimeException {
    public EmptyNameException() {
        super("The name, that was inserted, was empty, and it shouldn't be.");
    }
}
