package ru.otus.service;

import ru.otus.model.Training;
import ru.otus.model.dto.TrainingDtoInput;
import ru.otus.model.dto.TrainingForTraineeDtoOutput;
import ru.otus.model.dto.TrainingForTrainerDtoOutput;
import ru.otus.spec.TrainingTraineeSpecification;
import ru.otus.spec.TrainingTrainerSpecification;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TrainingService {

    Training save(TrainingDtoInput trainingDtoInput, HttpServletRequest request);

    List<TrainingForTraineeDtoOutput> findByDateRangeAndTraineeUsername(TrainingTraineeSpecification specification);

    List<TrainingForTrainerDtoOutput> findByDateRangeAndTrainerUsername(
            TrainingTrainerSpecification specification);

    void deleteById(Long id, HttpServletRequest request);
}
