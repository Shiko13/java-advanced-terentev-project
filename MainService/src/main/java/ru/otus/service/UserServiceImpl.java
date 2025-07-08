package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.exception.AccessException;
import ru.otus.exception.ErrorMessageConstants;
import ru.otus.exception.TooManyAttemptsException;
import ru.otus.model.User;
import ru.otus.model.dto.UserActivateDtoInput;
import ru.otus.model.dto.UserDtoInput;
import ru.otus.model.dto.UserWithPassword;
import ru.otus.repo.UserRepo;
import ru.otus.util.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    private final LoginAttemptServiceImpl loginAttemptService;

    private final PasswordEncoder passwordEncoder;
    private final Map<String, List<byte[]>> memoryLeakMap = new ConcurrentHashMap<>();

    @Value("${password.length}")
    private int passwordLength;

    @Override
    @Transactional
    public UserWithPassword save(UserDtoInput userDtoInput) {
        log.info("save, userDtoInput = {}", userDtoInput);

        return createEntireUser(userDtoInput);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        log.info("changePassword, username = {}", username);
        var user = getUserByUsername(username);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AccessException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public void switchActivate(String username, UserActivateDtoInput userInput) {
        log.info("switchActivate, username = {}", username);

        var user = getUserByUsername(username);

        user.setIsActive(userInput.getIsActive());
        userRepo.save(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        if (Character.isDigit(username.charAt(username.length() - 1))) {
            String[] parts = username.split("-");
            String userNameWithPostfix = parts[0].trim();
            Integer postfix = Integer.valueOf(parts[1]);
            return userRepo.findByUsernameAndPostfix(userNameWithPostfix, postfix);
        }

        return userRepo.findByUsernameAndPostfix(username, 0);
    }

    private User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new AccessException(ErrorMessageConstants.ACCESS_ERROR_MESSAGE));
    }

    public UserWithPassword createEntireUser(UserDtoInput userDtoInput) {
        String rawPassword = RandomStringGenerator.generateRandomString(passwordLength);
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String userName = userDtoInput.getFirstName().toLowerCase() + "." + userDtoInput.getLastName().toLowerCase();
        Integer maxPostfix = 0;

        if (isUsernameExistsInDatabase(userName)) {
            maxPostfix = userRepo.findMaxPostfixByUsername(userName);
            maxPostfix++;
        }

        // Симуляция утечки: 520 KB на каждого нового пользователя
        byte[] leakChunk = new byte[520_000];

        memoryLeakMap
                .computeIfAbsent(userName, k -> new ArrayList<>())
                .add(leakChunk);

        return UserWithPassword.builder()
                .firstName(userDtoInput.getFirstName())
                .lastName(userDtoInput.getLastName())
                .username(userName)
                .encodedPassword(encodedPassword)
                .rawPassword(rawPassword)
                .postfix(maxPostfix)
                .isActive(false)
                .build();

    }

    public boolean isUsernameExistsInDatabase(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isBlocked()) {
            throw new TooManyAttemptsException("Too many attempts. You will be unlocked in 5 minutes.");
        }

        var user = findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
