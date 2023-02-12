package com.example.groceryshoptill.controllers;

import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.services.contracts.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
public class BasketRestController {

    private final BasketService basketService;

    public BasketRestController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    @Operation(description = "Products can be added all at once as a JSON array. Returns the total price of them and clears the basket.")
    public String addAllAndCalculate(@RequestBody List<String> productNames) {
        try {
            return basketService.addAllAndCalculate(productNames);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/add")
    @Operation(description = "Products can be added to the basket one by one. Returns the added product.")
    public Product addProductToBasket(@RequestParam("productName") String productName) {
        try {
            return basketService.addProductToBasket(productName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/calculate")
    @Operation(description = "Returns the total price of products added to the basket one by one and clears the basket.")
    public String calculateTotalPrice() {
        return basketService.calculateTotalPrice();
    }

}
