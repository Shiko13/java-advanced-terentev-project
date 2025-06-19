package ru.otus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.dto.TrainingDtoInput;
import ru.otus.model.dto.TrainingForTraineeDtoOutput;
import ru.otus.model.dto.TrainingForTrainerDtoOutput;
import ru.otus.service.TrainingService;
import ru.otus.spec.TrainingTraineeSpecification;
import ru.otus.spec.TrainingTrainerSpecification;
import ru.otus.spec.filter.TrainingTraineeFilter;
import ru.otus.spec.filter.TrainingTrainerFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/training")
@Api(tags = "Training Controller")
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/criteria-trainee")
    @ApiOperation(value = "Find trainings of trainee by date range and trainer's name")
    public List<TrainingForTraineeDtoOutput> findByDateRangeAndTrainee(TrainingTraineeFilter filter) {
        return trainingService.findByDateRangeAndTraineeUsername(new TrainingTraineeSpecification(filter));
    }

    @GetMapping("/criteria-trainer")
    @ApiOperation(value = "Find trainings of trainer by date range and trainee's name")
    public List<TrainingForTrainerDtoOutput> findByDateRangeAndTrainer(TrainingTrainerFilter filter) {
        return trainingService.findByDateRangeAndTrainerUsername(new TrainingTrainerSpecification(filter));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Save Training", notes = "Create a new training based on the provided input.")
    public void save(@RequestBody TrainingDtoInput trainingDtoInput, HttpServletRequest request) {
        trainingService.save(trainingDtoInput, request);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Training", notes = "Delete a training by id.")
    public void deleteById(@RequestParam Long id, HttpServletRequest request) {
        trainingService.deleteById(id, request);
    }
}
