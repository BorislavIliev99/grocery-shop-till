package com.example.groceryshoptill.controllers;

import com.example.groceryshoptill.enums.DealType;
import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.helpers.AuthenticationHelper;
import com.example.groceryshoptill.mappers.ProductMapper;
import com.example.groceryshoptill.models.Deal;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.models.dtos.ProductDtoIn;
import com.example.groceryshoptill.services.contracts.DealService;
import com.example.groceryshoptill.services.contracts.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final DealService dealService;
    private final AuthenticationHelper authenticationHelper;

    public ProductRestController(ProductService productService,
                                 ProductMapper productMapper,
                                 DealService dealService,
                                 AuthenticationHelper authenticationHelper) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.dealService = dealService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    @Operation(description = "Returns all products in the shop.")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @Operation(description = "Creates a new product. Only admins have permission to create new products. " +
            "Product name must be unique.")
    public Product createProduct(@RequestHeader HttpHeaders headers,
                                 @RequestBody ProductDtoIn productDtoIn) {
        try {
            authenticationHelper.tryGetAdmin(headers);
            Product product = productMapper.toObject(productDtoIn);

            return productService.createProduct(product);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    @Operation(description = "Updates a product by given id. Only admins have permission to update products.")
    public Product updateProduct(@RequestHeader HttpHeaders headers,
                                 @PathVariable int productId,
                                 @RequestBody ProductDtoIn productDtoIn) {
        try {
            authenticationHelper.tryGetAdmin(headers);
            Product product = productMapper.toObject(productDtoIn);
            product.setId(productId);

            return productService.updateProduct(product);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    @Operation(description = "Deletes product by given id. Only admins have permission to delete products.")
    public void deleteProduct(@RequestHeader HttpHeaders headers,
                              @PathVariable int productId) {
        try {
            authenticationHelper.tryGetAdmin(headers);

            productService.deleteProduct(productId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{productId}/two-for-three")
    @Operation(description = "Adds product to two for three deal. " +
            "Only admins have permission to add products to deals.")
    public Deal addProductToTwoForThreeDeal(@RequestHeader HttpHeaders headers,
                                            @PathVariable int productId) {
        try {
            authenticationHelper.tryGetAdmin(headers);

            return dealService.addProductToDeal(productId, DealType.TWO_FOR_THREE);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/{productId}/buy-one-get-one-half-price")
    @Operation(description = "Adds product to buy one get one half price deal. " +
            "Only admins have permission to add products to deals.")
    public Deal buyOneGetOneHalfPrice(@RequestHeader HttpHeaders headers,
                                      @PathVariable int productId) {
        try {
            authenticationHelper.tryGetAdmin(headers);

            return dealService.addProductToDeal(productId, DealType.BUY_ONE_GET_ONE_HALF_PRICE);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
