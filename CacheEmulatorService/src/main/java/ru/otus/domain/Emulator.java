package ru.otus.domain;

import io.prometheus.client.Counter;
import ru.otus.interfaces.Cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Emulator {

    private Path cacheDirectory;
    private final Cache<String, String> cache;

    private static final Counter cacheHits = Counter.build()
            .name("cache_hits_total")
            .help("Total number of cache hits")
            .register();

    private static final Counter cacheMisses = Counter.build()
            .name("cache_misses_total")
            .help("Total number of cache misses")
            .register();

    public Emulator(Cache<String, String> cache) {
        this.cache = cache;
    }

    public void setCacheDirectory(String directoryPath) {
        if (directoryPath == null || directoryPath.isEmpty()) {
            throw new IllegalArgumentException("Directory path cannot be null or empty.");
        }

        Path path = Paths.get(directoryPath);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Directory cannot be created: " + e.getMessage(), e);
        }

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Invalid directory path: " + path);
        }

        cacheDirectory = path;
        System.out.println("Cache directory set to: " + cacheDirectory);
    }

    public void loadFileIntoCache(String fileName) {
        if (cacheDirectory == null) {
            throw new IllegalStateException("Cache directory is not set.");
        }

        Path filePath = cacheDirectory.resolve(fileName);
        if (Files.exists(filePath)) {
            try {
                String content = Files.readString(filePath);
                cache.load(fileName, content);
                System.out.println("File loaded into cache: " + fileName);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.err.println("File does not exist: " + fileName);
        }
    }

    public String getFileFromCache(String fileName) {
        Objects.requireNonNull(fileName, "File name must not be null");

        String content = cache.get(fileName);
        if (content == null) {
            cacheMisses.inc();
            System.out.println("Cache miss for: " + fileName);
            loadFileIntoCache(fileName);
            content = cache.get(fileName);
        } else {
            cacheHits.inc();
            System.out.println("Cache hit for: " + fileName);
        }

        return content;
    }
}
