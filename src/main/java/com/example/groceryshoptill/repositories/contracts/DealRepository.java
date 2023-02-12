package com.example.groceryshoptill.repositories.contracts;

import com.example.groceryshoptill.enums.DealType;
import com.example.groceryshoptill.models.Deal;
import com.example.groceryshoptill.models.Product;

import java.util.Optional;

public interface DealRepository {

    Optional<Deal> getDeal(int productId, DealType dealType);

    Deal addProductToDeal(Deal deal);

    boolean isProduct2For3(Product product);

    boolean isProductBuyOneGetOneHalfPrice(Product product);

}
