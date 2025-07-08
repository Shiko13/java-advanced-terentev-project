package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.domain.Emulator;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ParallelFileLoader {

    private final Emulator emulator;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ParallelFileLoader(Emulator emulator) {
        this.emulator = emulator;
    }

    public Map<String, String> loadFiles(List<String> filenames) throws InterruptedException {
        var results = new ConcurrentHashMap<String, String>();
        var latch = new CountDownLatch(filenames.size());

        for (String filename : filenames) {
            executor.submit(() -> {
                try {
                    String content = emulator.getFileFromCache(filename);
                    results.put(filename, content);
                } catch (Exception e) {
                    log.error("Failed to load file: " + filename, e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        return results;
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}