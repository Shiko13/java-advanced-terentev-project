package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.domain.Emulator;

import java.util.concurrent.Semaphore;

@Slf4j
@Service
public class SemaphoreFileAccessLimiter {

    private final Emulator emulator;
    private final Semaphore semaphore = new Semaphore(5);

    public SemaphoreFileAccessLimiter(Emulator emulator) {
        this.emulator = emulator;
    }

    public String getFileWithLimit(String filename) {
        try {
            semaphore.acquire();
            return emulator.getFileFromCache(filename);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for file access", e);
        } finally {
            semaphore.release();
        }
    }
}
