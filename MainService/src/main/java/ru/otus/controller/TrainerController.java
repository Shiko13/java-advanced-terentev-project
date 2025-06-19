package ru.otus.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.dto.*;
import ru.otus.service.TrainerService;

import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
@Api(tags = "Trainer Controller")
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping("/username")
    @ApiOperation("Get trainer by username")
    public TrainerDtoOutput getProfile(@RequestParam @Pattern(regexp = "[a-z]+\\.[\\w-]+",
            message = "Invalid input format") String username) {
        return trainerService.getByUsername(username);
    }

    @GetMapping("/free")
    @ApiOperation("Get not assigned on trainee active trainers")
    public List<TrainerForTraineeDtoOutput> getTrainersWithEmptyTrainees() {
        return trainerService.getTrainersWithEmptyTrainees();
    }

    @PostMapping()
    @ApiOperation("Trainer registration")
    public TrainerSaveDtoOutput registration(@RequestBody TrainerDtoInput trainerDtoInput) {
        return trainerService.save(trainerDtoInput);
    }

    @PutMapping("/profile")
    @ApiOperation("Update Trainer Profile")
    public TrainerUpdateDtoOutput updateProfile(@RequestParam String username,
                                                @RequestBody TrainerProfileDtoInput trainerDtoInput) {
        return trainerService.updateProfile(username, trainerDtoInput);
    }
}
