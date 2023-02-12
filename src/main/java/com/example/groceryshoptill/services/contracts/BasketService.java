package com.example.groceryshoptill.services.contracts;

import com.example.groceryshoptill.models.Product;

import java.util.List;

public interface BasketService {

    Product addProductToBasket(String name);

    String calculateTotalPrice();

    String addAllAndCalculate(List<String> productNames);

}
