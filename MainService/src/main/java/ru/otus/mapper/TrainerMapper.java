package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.otus.model.Trainer;
import ru.otus.model.User;
import ru.otus.model.dto.*;

@Mapper(componentModel = "spring", uses = {TraineeMapper.class, TrainingTypeMapper.class})
public interface TrainerMapper {

    @Mapping(target = "password", source = "rawPassword")
    @Mapping(target = "username", expression = "java(appendPostfix(trainer.getUser()))")
    TrainerSaveDtoOutput toSaveDto(Trainer trainer, String rawPassword);

    Trainer toEntity(TrainerDtoInput trainerDtoInput);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.isActive")
    @Mapping(target = "specialization", source = "trainingType")
    TrainerDtoOutput toDtoOutput(Trainer trainer);

    @Mapping(target = "username", expression = "java(appendPostfix(trainer.getUser()))")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "specialization", source = "trainingType")
    TrainerForTraineeDtoOutput toTrainerForTraineeDtoOutput(Trainer trainer);

    @Mapping(target = "username", expression = "java(appendPostfix(trainer.getUser()))")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.isActive")
    @Mapping(target = "specialization", source = "trainingType")
    TrainerUpdateDtoOutput toTrainerUpdateDtoOutput(Trainer trainer);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.isActive", source = "isActive")
    void updateTrainerProfile(@MappingTarget Trainer existingTrainer, TrainerProfileDtoInput trainerProfileDtoInput);

    default String appendPostfix(User user) {
        return (user.getPostfix() != 0) ? user.getUsername() + "-" + user.getPostfix() : user.getUsername();
    }
}
