package com.example.groceryshoptill.services;

import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void getAllProducts_Should_CallRepository() {
        productService.getAllProducts();

        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    public void getAllProducts_Should_ReturnListOfProducts() {
        Product product1 = Product.builder().build();
        Product product2 = Product.builder().build();

        List<Product> list = List.of(product1, product2);

        when(productService.getAllProducts()).thenReturn(list);

        List<Product> returnedList = productService.getAllProducts();

        assertIterableEquals(list, returnedList, "Returned list is not as expected");
    }

    @Test
    public void updateProduct_Should_CallRepository_When_ProductExists() {
        Product product = Product.builder()
                .id(1)
                .build();
        when(productRepository.getProductById(product.getId())).thenReturn(Optional.of(product));

        productService.updateProduct(product);

        verify(productRepository, times(1)).updateProduct(product);
    }

    @Test
    public void updateProduct_Should_ThrowException_When_ProductDoesNotExists() {
        Product product = Product.builder()
                .id(1)
                .build();
        when(productRepository.getProductById(product.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(product));
    }

    @Test
    public void deleteProduct_Should_CallRepository_When_ProductExists() {
        Product product = Product.builder()
                .id(1)
                .build();
        when(productRepository.getProductById(product.getId())).thenReturn(Optional.of(product));

        productService.deleteProduct(product.getId());

        verify(productRepository, times(1)).deleteProduct(product);
    }

    @Test
    public void deleteProduct_Should_ThrowException_When_ProductDoesNotExists() {
        Product product = Product.builder()
                .id(1)
                .build();
        when(productRepository.getProductById(product.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(product.getId()));
    }

    @Test
    public void addProductToDB_Should_ThrowException_When_ProductAlreadyExists() {
        Product product = Product.builder()
                .name("Apple")
                .build();
        when(productRepository.getProductByName(product.getName())).thenReturn(Optional.of(product));

        assertThrows(DuplicateEntityException.class, () -> productService.createProduct(product));
    }

    @Test
    public void addProductToDB_Should_CallRepository_When_ProductDoesNotAlreadyExist() {
        Product product = Product.builder()
                .name("Apple")
                .build();
        when(productRepository.getProductByName(product.getName())).thenReturn(Optional.empty());

        productService.createProduct(product);

        verify(productRepository, times(1)).createProduct(product);
    }

}
