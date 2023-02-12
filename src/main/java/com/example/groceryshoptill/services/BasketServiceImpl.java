package com.example.groceryshoptill.services;

import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.DealRepository;
import com.example.groceryshoptill.repositories.contracts.ProductRepository;
import com.example.groceryshoptill.services.contracts.BasketService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

@Service
public class BasketServiceImpl implements BasketService {

    private final Queue<Product> basket;
    private final ProductRepository productRepository;
    private final DealRepository dealRepository;

    public BasketServiceImpl(ProductRepository productRepository,
                             DealRepository dealRepository) {
        this.basket = new LinkedList<>();
        this.dealRepository = dealRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product addProductToBasket(String productName) {
        Optional<Product> product = productRepository.getProductByName(productName);

        if (product.isPresent()) {
            basket.add(product.get());

            return product.get();
        }

        throw new EntityNotFoundException("Product", productName);
    }

    @Override
    public String calculateTotalPrice() {
        double totalPrice = 0;
        int counter = 0;
        double minPrice = Double.MAX_VALUE;
        Map<Product, Integer> productsBuy1Get1Half = new HashMap<>();
        Product product;
        int basketSize = basket.size();

        for (int i = 0; i < basketSize; i++) {
            product = basket.poll();
            totalPrice += product.getPrice();

            if (dealRepository.isProduct2For3(product)) {
                ++counter;
                minPrice = Math.min(minPrice, product.getPrice());

                if (counter % 3 == 0) {
                    totalPrice -= minPrice;
                    minPrice = Double.MAX_VALUE;
                    continue;
                }
            }

            if (dealRepository.isProductBuyOneGetOneHalfPrice(product)) {
                totalPrice -= calculateBuyOneGetOneHalfPrice(productsBuy1Get1Half, product);
            }
        }

        DecimalFormat df = new DecimalFormat();

        return df.format(totalPrice) + " aws";
    }

    @Override
    public String addAllAndCalculate(List<String> productNames) {

        for (String productName : productNames) {
            addProductToBasket(productName);
        }

        return calculateTotalPrice();
    }

    private Double calculateBuyOneGetOneHalfPrice(Map<Product, Integer> productsBuy1Get1Half, Product product) {
        int val = 0;

        if (productsBuy1Get1Half.containsKey(product)) {
            val = productsBuy1Get1Half.get(product);
            productsBuy1Get1Half.put(product, ++val);

            if (val % 2 == 0) {
                return product.getPrice() / 2;
            }
        }

        productsBuy1Get1Half.put(product, ++val);

        return 0.0;
    }

}
