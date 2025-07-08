package ru.otus.benchmark;

import org.openjdk.jmh.annotations.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.model.dto.UserDtoInput;
import ru.otus.model.dto.UserWithPassword;
import ru.otus.repo.UserRepo;
import ru.otus.service.LoginAttemptServiceImpl;
import ru.otus.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class UserServiceBenchmark {

    private UserServiceImpl userService;

    @Setup(Level.Iteration)
    public void setUp() {
        UserRepo userRepo = new MockUserRepo();
        HttpServletRequest request = new MockHttpServletRequest();
        LoginAttemptServiceImpl loginAttemptService = new LoginAttemptServiceImpl(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        userService = new UserServiceImpl(userRepo, loginAttemptService, passwordEncoder);
    }

    @Benchmark
    public UserWithPassword testCreateUser() {
        UserDtoInput input = new UserDtoInput("Alice", "Smith");
        return userService.save(input);
    }
}

