package com.example.groceryshoptill.helpers;

import com.example.groceryshoptill.exceptions.EntityNotFoundException;
import com.example.groceryshoptill.exceptions.UnauthorizedException;
import com.example.groceryshoptill.models.Admin;
import com.example.groceryshoptill.services.contracts.AdminService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationHelper {

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Invalid username or password";

    private final AdminService adminService;

    public AuthenticationHelper(AdminService adminService) {
        this.adminService = adminService;
    }

    public void tryGetAdmin(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "The requested operation requires authentication");
        }

        try {
            String headerValue = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String[] userInfo = headerValue.split(" ");
            if (userInfo[0].isEmpty() || userInfo[1].isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS_MESSAGE);
            }

            adminService.confirmCredentials(userInfo[0], userInfo[1].toCharArray());
        } catch (NullPointerException |
                 ArrayIndexOutOfBoundsException |
                 UnauthorizedException |
                 EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, WRONG_CREDENTIALS_MESSAGE);
        }
    }

}
