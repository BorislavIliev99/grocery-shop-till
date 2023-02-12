package com.example.groceryshoptill.services.contracts;

import com.example.groceryshoptill.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(int productId);

}
