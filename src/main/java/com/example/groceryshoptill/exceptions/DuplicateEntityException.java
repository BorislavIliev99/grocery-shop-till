package com.example.groceryshoptill.exceptions;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String type, String name) {
        super(String.format("%s with name %s already exists", type, name));
    }

}
