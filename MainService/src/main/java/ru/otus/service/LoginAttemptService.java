package ru.otus.service;

public interface LoginAttemptService {

    void loginFailed(final String key);

    boolean isBlocked();
}
