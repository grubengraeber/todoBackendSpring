package com.grubengraeber.todo.exception.exceptions;

public class NoListFoundException extends RuntimeException {
    public NoListFoundException(long id) {
        super("No List with ID: " + id + " found.");
    }
}
