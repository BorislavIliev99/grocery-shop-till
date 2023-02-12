package com.example.groceryshoptill.repositories.contracts;

import com.example.groceryshoptill.models.Admin;

import java.util.Optional;

public interface AdminRepository {

    Admin register(Admin admin);

    Optional<Admin> getAdminByUsername(String username);

}
