package com.example.groceryshoptill.mappers;

import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.models.dtos.ProductDtoIn;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toObject(ProductDtoIn productDtoIn) {
        return new Product(null, productDtoIn.getName(), productDtoIn.getPrice());
    }
}
