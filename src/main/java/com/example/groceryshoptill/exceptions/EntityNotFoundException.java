package com.example.groceryshoptill.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type, int id) {
        super(String.format("%s with id %d is not found", type, id));
    }

    public EntityNotFoundException(String type, String name) {
        super(String.format("%s with name %s is not found", type, name));
    }

}
