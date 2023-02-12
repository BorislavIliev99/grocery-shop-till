package com.example.groceryshoptill.services;

import com.example.groceryshoptill.exceptions.DuplicateEntityException;
import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.helpers.PasswordEncoderHelper;
import com.example.groceryshoptill.models.Admin;
import com.example.groceryshoptill.repositories.contracts.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTests {

    @Mock
    AdminRepository adminRepository;

    @InjectMocks
    AdminServiceImpl adminService;

    @Test
    public void register_Should_CallRepository_When_AdminWithSuchUsernameDoesNotExist() {
        Admin admin = Admin.builder()
                .username("Username")
                .build();

        when(adminRepository.getAdminByUsername(admin.getUsername())).thenReturn(Optional.empty());

        adminService.register(admin);

        verify(adminRepository, times(1)).register(admin);
    }

    @Test
    public void register_Should_ThrowException_When_AdminAlreadyExists() {
        Admin admin = Admin.builder()
                .username("Username")
                .build();

        when(adminRepository.getAdminByUsername(admin.getUsername())).thenReturn(Optional.of(admin));

        assertThrows(DuplicateEntityException.class, () -> adminService.register(admin));
    }

    @Test
    public void confirmCredentials_Should_ThrowException_When_ThereIsNoAdminWithThatUsername() {
        when(adminRepository.getAdminByUsername("username")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                adminService.confirmCredentials("username", "password".toCharArray()));
    }

}
