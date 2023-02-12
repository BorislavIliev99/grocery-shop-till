package com.example.groceryshoptill.mappers;

import com.example.groceryshoptill.helpers.PasswordEncoderHelper;
import com.example.groceryshoptill.models.Admin;
import com.example.groceryshoptill.models.dtos.AdminDtoIn;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.Arrays;

@Component
public class AdminMapper {

    private final PasswordEncoderHelper passwordEncoderHelper;

    public AdminMapper(PasswordEncoderHelper passwordEncoderHelper) {
        this.passwordEncoderHelper = passwordEncoderHelper;
    }

    public Admin toObject(AdminDtoIn adminDtoIn) {
        CharSequence cs = CharBuffer.wrap(adminDtoIn.getPassword());
        String hashedPassword = passwordEncoderHelper.encode(cs);
        char[] password = adminDtoIn.getPassword();
        Arrays.fill(password, (char) 0);

        return new Admin(null, adminDtoIn.getUsername(), hashedPassword.toCharArray());
    }

}
