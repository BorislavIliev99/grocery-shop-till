package com.example.groceryshoptill.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationHelperTests {

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @InjectMocks
    AuthenticationHelper authenticationHelper;

    @Test
    public void tryGetAdmin_Should_ThrowException_When_ThereIsNoAuthorizationKey() {
        HttpHeaders headers = mock(HttpHeaders.class);
        when(headers.containsKey(AUTHORIZATION_HEADER_NAME)).thenReturn(false);

        Assertions.assertThrows(ResponseStatusException.class, () -> authenticationHelper.tryGetAdmin(headers));
    }

    @Test
    public void tryGetAdmin_Should_ThrowException_When_AuthorizationHeaderIsEmpty() {
        HttpHeaders headers = mock(HttpHeaders.class);
        when(headers.containsKey(AUTHORIZATION_HEADER_NAME)).thenReturn(true);

        Assertions.assertThrows(ResponseStatusException.class, () -> authenticationHelper.tryGetAdmin(headers));
    }

    @Test
    public void tryGetAdmin_Should_ThrowException_When_ThereIsNoPassword() {
        HttpHeaders headers = mock(HttpHeaders.class);
        when(headers.containsKey(AUTHORIZATION_HEADER_NAME)).thenReturn(true);
        when(headers.getFirst(AUTHORIZATION_HEADER_NAME)).thenReturn("username ");

        Assertions.assertThrows(ResponseStatusException.class, () -> authenticationHelper.tryGetAdmin(headers));
    }

    @Test
    public void tryGetAdmin_Should_ThrowException_When_ThereIsNoUsername() {
        HttpHeaders headers = mock(HttpHeaders.class);
        when(headers.containsKey(AUTHORIZATION_HEADER_NAME)).thenReturn(true);
        when(headers.getFirst(AUTHORIZATION_HEADER_NAME)).thenReturn(" password");

        Assertions.assertThrows(ResponseStatusException.class, () -> authenticationHelper.tryGetAdmin(headers));
    }

}
