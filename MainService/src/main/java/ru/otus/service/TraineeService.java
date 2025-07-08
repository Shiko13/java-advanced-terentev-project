package ru.otus.service;

import ru.otus.model.dto.*;

import java.util.List;

public interface TraineeService {

    TraineeSaveDtoOutput save(TraineeDtoInput traineeDtoInput);

    TraineeDtoOutput getByUsername(String username);

    TraineeUpdateDtoOutput updateProfile(String userName, TraineeProfileDtoInput traineeDtoInput);

    TraineeUpdateListDtoOutput updateTrainerList(String username,
                                                 List<TrainerShortDtoInput> trainersUsernames);

    void deleteByUsername(String username);
}
