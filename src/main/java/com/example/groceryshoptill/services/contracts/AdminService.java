package com.example.groceryshoptill.services.contracts;

import com.example.groceryshoptill.models.Admin;

public interface AdminService {

    Admin register(Admin admin);

    Admin confirmCredentials(String username, char[] password);

}
