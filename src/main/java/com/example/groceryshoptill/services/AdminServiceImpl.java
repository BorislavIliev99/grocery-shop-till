package com.example.groceryshoptill.services;

import com.example.groceryshoptill.helpers.PasswordEncoderHelper;
import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.exceptions.UnauthorizedException;
import com.example.groceryshoptill.models.Admin;
import com.example.groceryshoptill.repositories.contracts.AdminRepository;
import com.example.groceryshoptill.services.contracts.AdminService;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoderHelper passwordEncoderHelper;

    public AdminServiceImpl(AdminRepository adminRepository,
                            PasswordEncoderHelper passwordEncoderHelper) {
        this.adminRepository = adminRepository;
        this.passwordEncoderHelper = passwordEncoderHelper;
    }

    @Override
    public Admin register(Admin admin) {
        Optional<Admin> optionalAdmin = adminRepository.getAdminByUsername(admin.getUsername());

        if (optionalAdmin.isEmpty()) {
            return adminRepository.register(admin);
        }

        throw new DuplicateEntityException("Admin", admin.getUsername());
    }

    @Override
    public Admin confirmCredentials(String username, char[] password) {
        Optional<Admin> admin = adminRepository.getAdminByUsername(username);

        if (admin.isEmpty()) {
            throw new EntityNotFoundException("Admin", username);
        }

        String hashedPassword = new String(admin.get().getPassword());

        if (!passwordEncoderHelper.matches(CharBuffer.wrap(password), hashedPassword)) {
            throw new UnauthorizedException("You are not authorized");
        }

        return admin.get();
    }

}
