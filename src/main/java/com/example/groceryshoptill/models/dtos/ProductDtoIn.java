package com.example.groceryshoptill.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDtoIn {

    @NotEmpty(message = "Cannot create product without name")
    private String name;

    @NotEmpty(message = "Cannot create product without price")
    @Positive(message = "Price must be a positive number")
    private Double price;

}
