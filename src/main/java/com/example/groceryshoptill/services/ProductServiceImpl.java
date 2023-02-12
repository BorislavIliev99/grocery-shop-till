package com.example.groceryshoptill.services;

import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.ProductRepository;
import com.example.groceryshoptill.services.contracts.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.getProductByName(product.getName());

        if (optionalProduct.isEmpty()) {
            return productRepository.createProduct(product);
        }

        throw new DuplicateEntityException("Product", product.getName());
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.getProductById(product.getId());

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product", product.getId());
        }

        return productRepository.updateProduct(product);
    }

    @Override
    public void deleteProduct(int productId) {
        Optional<Product> optionalProduct = productRepository.getProductById(productId);

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product", productId);
        }

        productRepository.deleteProduct(optionalProduct.get());
    }

}
