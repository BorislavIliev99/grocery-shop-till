package com.example.groceryshoptill.services;

import com.example.groceryshoptill.enums.DealType;
import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.models.Deal;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.DealRepository;
import com.example.groceryshoptill.repositories.contracts.ProductRepository;
import com.example.groceryshoptill.services.contracts.DealService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final ProductRepository productRepository;

    public DealServiceImpl(DealRepository dealRepository,
                           ProductRepository productRepository) {
        this.dealRepository = dealRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Deal addProductToDeal(int productId, DealType dealType) {
        Optional<Product> optionalProduct = productRepository.getProductById(productId);

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product", productId);
        }

        Optional<Deal> optionalDeal = dealRepository.getDeal(productId, dealType);

        if (optionalDeal.isPresent()) {
            throw new DuplicateEntityException(String.format("This product is already added to %s deal",
                    dealType.toString()));
        }

        Deal deal = new Deal(null, optionalProduct.get(), dealType);
        return dealRepository.addProductToDeal(deal);
    }

}
