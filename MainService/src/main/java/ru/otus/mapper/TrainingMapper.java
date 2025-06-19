package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.model.Training;
import ru.otus.model.dto.TrainingDtoInput;
import ru.otus.model.dto.TrainingForTraineeDtoOutput;
import ru.otus.model.dto.TrainingForTrainerDtoOutput;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {TrainerMapper.class, TraineeMapper.class, TrainingTypeMapper.class})
public interface TrainingMapper {

    Training toEntity(TrainingDtoInput trainingDtoInput);

    List<TrainingForTraineeDtoOutput> toTrainingForTraineeDtoList(List<Training> trainings);

    List<TrainingForTrainerDtoOutput> toTrainingForTrainerDtoList(List<Training> trainings);

    @Mapping(target = "type", source = "trainingType.name")
    @Mapping(target = "trainerName", source = "trainer.user.username")
    TrainingForTraineeDtoOutput toTrainingForTraineeDto(Training training);

    @Mapping(target = "type", source = "trainingType.name")
    @Mapping(target = "traineeName", source = "trainee.user.username")
    TrainingForTrainerDtoOutput toTrainingForTrainerDto(Training training);
}
