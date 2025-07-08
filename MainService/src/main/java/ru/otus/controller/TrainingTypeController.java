package ru.otus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.model.dto.TrainingTypeOutputDto;
import ru.otus.service.TrainingTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/training-types")
@Api(tags = "Training types Controller")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    @GetMapping
    @ApiOperation(value = "Find all training's types")
    public List<TrainingTypeOutputDto> getAll() {
        return trainingTypeService.getAll();
    }
}
