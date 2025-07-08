package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Emulator;

@RestController
@RequestMapping("/cache")
public class ProgramCacheController {

    private final Emulator emulator;

    public ProgramCacheController(Emulator emulator) {
        this.emulator = emulator;
    }

    @GetMapping("/program/{name}")
    public ResponseEntity<String> getProgram(@PathVariable String name) {
        String result = emulator.getFileFromCache(name);
        return (result != null)
                ? ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();
    }
}
