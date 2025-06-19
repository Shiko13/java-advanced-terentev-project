package ru.otus.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.controller.UserController;
import ru.otus.exception.AccessException;
import ru.otus.model.dto.UserActivateDtoInput;
import ru.otus.service.UserService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void changePassword_ShouldOk() {
        String username = "testUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        doNothing().when(userService).changePassword(username, oldPassword, newPassword);

        userController.changePassword(username, oldPassword, newPassword);

        verify(userService).changePassword(username, oldPassword, newPassword);
    }

    @Test
    void changePassword_ShouldThrowAccessException() {
        String username = "testUser";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        doThrow(AccessException.class).when(userService).changePassword(username, oldPassword, newPassword);

        assertThrows(AccessException.class, () -> userController.changePassword(username, oldPassword, newPassword));

        verify(userService).changePassword(username, oldPassword, newPassword);
    }

    @Test
    void switchActivate_ShouldOk() {
        String username = "testUser";
        UserActivateDtoInput userInput = new UserActivateDtoInput(true);

        doNothing().when(userService).switchActivate(username, userInput);

        userController.switchActivate(username, userInput);

        verify(userService).switchActivate(username, userInput);
    }

    @Test
    void switchActivate_ShouldThrowAccessException() {
        String username = "testUser";
        UserActivateDtoInput userInput = new UserActivateDtoInput(true);

        doThrow(AccessException.class).when(userService).switchActivate(username, userInput);

        assertThrows(AccessException.class, () -> userController.switchActivate(username, userInput));

        verify(userService).switchActivate(username, userInput);
    }
}
