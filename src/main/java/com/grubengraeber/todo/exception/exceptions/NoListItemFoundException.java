package com.grubengraeber.todo.exception.exceptions;

public class NoListItemFoundException extends RuntimeException {
    public NoListItemFoundException(long id) {
        super("No List Item with ID: " + id + " found.");
    }
}
