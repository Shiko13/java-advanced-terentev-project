package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.dto.TrainerWorkloadDto;
import ru.otus.service.TrainerService;

@RestController
@RequestMapping("trainer-another")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void save(@RequestBody TrainerWorkloadDto trainerWorkloadDto) {
        trainerService.save(trainerWorkloadDto);
    }
}