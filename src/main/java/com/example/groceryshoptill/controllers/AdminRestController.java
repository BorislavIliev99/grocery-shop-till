package com.example.groceryshoptill.controllers;

import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.helpers.AuthenticationHelper;
import com.example.groceryshoptill.mappers.AdminMapper;
import com.example.groceryshoptill.models.Admin;
import com.example.groceryshoptill.models.dtos.AdminDtoIn;
import com.example.groceryshoptill.services.contracts.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admins")
public class AdminRestController {

    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final AuthenticationHelper authenticationHelper;

    public AdminRestController(AdminService adminService,
                               AdminMapper adminMapper,
                               AuthenticationHelper authenticationHelper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping
    @Operation(description = "Registers new admin. Only admins has permission to register." +
            "The password from the passed JSON object is hashed in the database.")
    public Admin register(@RequestHeader HttpHeaders headers,
                          @RequestBody AdminDtoIn adminDtoIn) {
        try {
            authenticationHelper.tryGetAdmin(headers);

            Admin newAdmin = adminMapper.toObject(adminDtoIn);

            return adminService.register(newAdmin);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
