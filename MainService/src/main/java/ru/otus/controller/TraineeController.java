package ru.otus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.dto.*;
import ru.otus.service.TraineeService;

import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/trainee")
@Api(tags = "Trainee Controller")
public class TraineeController {

    private final TraineeService traineeService;

    @GetMapping("/username")
    @ApiOperation("Get trainee profile by username")
    public TraineeDtoOutput getProfile(
            @RequestParam @Pattern(regexp = "[a-z]+\\.[\\w-]+", message = "Invalid input format") String username) {
        return traineeService.getByUsername(username);
    }

    @PostMapping()
    @ApiOperation("Trainee registration")
    public TraineeSaveDtoOutput registration(@RequestBody TraineeDtoInput traineeDtoInput) {
        return traineeService.save(traineeDtoInput);
    }

    @PutMapping("/profile")
    @ApiOperation("Update trainee profile")
    public TraineeUpdateDtoOutput updateProfile(@RequestParam String username,
                                                @RequestBody TraineeProfileDtoInput traineeDtoInput) {
        return traineeService.updateProfile(username, traineeDtoInput);
    }

    @PutMapping("/trainer-list")
    @ApiOperation("Update trainee's trainer list")
    public TraineeUpdateListDtoOutput updateTrainerList(@RequestParam String username,
                                                        @RequestBody List<TrainerShortDtoInput> trainersUsernames) {
        return traineeService.updateTrainerList(username, trainersUsernames);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Delete trainee by username")
    public void deleteByUsername(@RequestParam String username) {
        traineeService.deleteByUsername(username);
    }
}
