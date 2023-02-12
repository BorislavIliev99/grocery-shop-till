package com.example.groceryshoptill.services;

import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasketServiceImplTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    BasketServiceImpl basketService;

    @Test
    public void addProductToBasket_Should_AddTheProductToTheBasketQueue() {
        Product product = Product.builder()
                .name("Apple")
                .build();
        when(productRepository.getProductByName(product.getName()))
                .thenReturn(Optional.of(product));

        Product result = basketService.addProductToBasket(product.getName());

        assertEquals(product, result);
    }

    @Test
    public void addProductToBasket_Should_ThrowException_When_ProductNotFound() {
        Product product = Product.builder()
                .name("Apple")
                .build();
        when(productRepository.getProductByName(product.getName()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> basketService.addProductToBasket(product.getName()));
    }

}
