package ru.otus.service;

import ru.otus.model.User;
import ru.otus.model.dto.UserActivateDtoInput;
import ru.otus.model.dto.UserDtoInput;
import ru.otus.model.dto.UserWithPassword;

import java.util.Optional;

public interface UserService {

    UserWithPassword save(UserDtoInput userDtoInput);

    void changePassword(String username, String oldPassword, String newPassword);

    void switchActivate(String username, UserActivateDtoInput userInput);

    Optional<User> findUserByUsername(String username);
}
