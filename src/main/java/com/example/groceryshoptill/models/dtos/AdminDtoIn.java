package com.example.groceryshoptill.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDtoIn {

    @NotEmpty(message = "You should provide name")
    private String username;

    @NotEmpty(message = "Choose a password")
    private char[] password;

}
