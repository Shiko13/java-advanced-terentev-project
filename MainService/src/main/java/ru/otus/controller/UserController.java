package ru.otus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.dto.UserActivateDtoInput;
import ru.otus.service.UserService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User Controller")
public class UserController {

    private final UserService userService;

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Change password",
            notes = "Change user password based on provided credentials and new password")
    public void changePassword(@RequestParam String username, @RequestParam String oldPassword,
                               @RequestParam String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    @PatchMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Switch user activity")
    public void switchActivate(@RequestParam String username,
                               @RequestBody UserActivateDtoInput userInput) {
        userService.switchActivate(username, userInput);
    }
}
