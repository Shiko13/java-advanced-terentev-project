package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Service
public class LockingEmulator {

    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public String getFile(String filename) {
        lock.readLock().lock();
        try {
            return cache.computeIfAbsent(filename, this::slowLoad);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void clearCache() {
        lock.writeLock().lock();
        try {
            cache.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private String slowLoad(String filename) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "data for " + filename;
    }
}
