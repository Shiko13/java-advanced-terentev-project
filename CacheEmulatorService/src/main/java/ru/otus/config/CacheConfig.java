package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.domain.Emulator;
import ru.otus.interfaces.impl.SoftCache;

@Configuration
public class CacheConfig {

    @Value("${emulator.cache-dir}")
    private String cacheDir;

    @Bean
    public Emulator emulator() {
        SoftCache<String> cache = new SoftCache<>();
        Emulator emulator = new Emulator(cache);
        emulator.setCacheDirectory(cacheDir);
        return emulator;
    }
}
