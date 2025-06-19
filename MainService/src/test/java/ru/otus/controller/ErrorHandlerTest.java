package ru.otus.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.controller.ErrorHandler;
import ru.otus.exception.AccessException;
import ru.otus.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void handleAccessException_ShouldReturnUnauthorizedResponse() {
        AccessException accessException = new AccessException("Unauthorized access");

        String textError = errorHandler.handleAccessException(accessException);

        assertEquals("Unauthorized access", textError);
    }

    @Test
    void handleNotFoundException_ShouldReturnNotFoundResponse() {
        NotFoundException notFoundException = new NotFoundException("Resource not found");

        String textError = errorHandler.handleNotFoundException(notFoundException);

        assertEquals("Resource not found", textError);
    }
}