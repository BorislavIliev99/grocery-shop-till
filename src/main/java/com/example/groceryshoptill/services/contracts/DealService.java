package com.example.groceryshoptill.services.contracts;

import com.example.groceryshoptill.enums.DealType;
import com.example.groceryshoptill.models.Deal;

public interface DealService {

    Deal addProductToDeal(int productId, DealType dealType);

}
