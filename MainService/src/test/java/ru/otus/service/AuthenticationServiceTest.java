package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.otus.model.dto.AuthResponse;
import ru.otus.service.AuthenticationServiceImpl;
import ru.otus.util.JwtProvider;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void login_ShouldReturnAuthToken() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(username, password));

        when(jwtProvider.generateToken(username)).thenReturn("mockedAuthToken");

        AuthResponse authResponse = authenticationService.login(username, password);

        assertNotNull(authResponse);
        assertNotNull(authResponse.getToken());
        assertEquals("mockedAuthToken", authResponse.getToken());
    }

    @Test
    void logout_ShouldInvalidateToken() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(jwtProvider.resolveToken(mockRequest)).thenReturn("mockedToken");

        authenticationService.logout(mockRequest);

        verify(jwtProvider).invalidateToken("mockedToken");
    }
}
