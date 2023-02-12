package com.example.groceryshoptill.repositories.contracts;

import com.example.groceryshoptill.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getAllProducts();

    Optional<Product> getProductByName(String name);

    Optional<Product> getProductById(int productId);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Product product);

}
