package com.example.groceryshoptill.services;

import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.models.Deal;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.DealRepository;
import com.example.groceryshoptill.repositories.contracts.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.groceryshoptill.enums.DealType.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DealServiceImplTests {

    @Mock
    DealRepository dealRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    DealServiceImpl dealService;

    @Test
    public void addProductToDeal_Should_ThrowException_When_ProductDoesNotExist() {
        when(productRepository.getProductById(0)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> dealService.addProductToDeal(0, TWO_FOR_THREE));
    }

    @Test
    public void addProductToDeal_Should_ThrowException_WhenSuchDealAlreadyExists() {
        when(productRepository.getProductById(0)).thenReturn(Optional.of(Product.builder().build()));

        when(dealRepository.getDeal(0, TWO_FOR_THREE)).thenReturn(Optional.of(Deal.builder().build()));

        assertThrows(DuplicateEntityException.class,
                () -> dealService.addProductToDeal(0, TWO_FOR_THREE));
    }

    @Test
    public void addProductToDeal_Should_CallRepository_When_ProductExistsAndDealDoesNot() {
        when(productRepository.getProductById(0)).thenReturn(Optional.of(Product.builder().build()));

        when(dealRepository.getDeal(0, TWO_FOR_THREE)).thenReturn(Optional.empty());

        dealService.addProductToDeal(0, TWO_FOR_THREE);

        verify(dealRepository, times(1)).addProductToDeal(any(Deal.class));
    }

}
