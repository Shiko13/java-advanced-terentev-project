package ru.otus.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.dto.AuthResponse;
import ru.otus.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/login")
    @ApiOperation(value = "Login page", notes = "After login will be send Bearer token")
    public AuthResponse login(@RequestParam String username, @RequestParam String password) throws Exception {
        return authenticationService.login(username, password);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/logout")
    @ApiOperation(value = "Logout page", notes = "After logout Bearer token will be add to blacklist")
    public String logout(HttpServletRequest request) {
        authenticationService.logout(request);

        return "Logout successful";
    }

    @GetMapping
    public String mainPage() {
        return "Main page will be here";
    }
}
