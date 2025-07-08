package ru.otus.service;

import ru.otus.model.dto.*;

import java.util.List;

public interface TrainerService {

    TrainerSaveDtoOutput save(TrainerDtoInput trainerDtoInput);

    TrainerDtoOutput getByUsername(String username);

    TrainerUpdateDtoOutput updateProfile(String username, TrainerProfileDtoInput trainerDtoInput);

    List<TrainerForTraineeDtoOutput> getTrainersWithEmptyTrainees();
}
