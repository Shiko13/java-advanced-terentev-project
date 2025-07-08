package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.otus.model.Trainee;
import ru.otus.model.User;
import ru.otus.model.dto.*;

@Mapper(componentModel = "spring")
public interface TraineeMapper {

    @Mapping(target = "password", source = "rawPassword")
    @Mapping(target = "username", expression = "java(appendPostfix(trainee.getUser()))")
    TraineeSaveDtoOutput toSaveDto(Trainee trainee, String rawPassword);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.isActive")
    @Mapping(target = "username", expression = "java(appendPostfix(trainee.getUser()))")
    TraineeUpdateDtoOutput toTraineeUpdateDto(Trainee trainee);

    Trainee toEntity(TraineeDtoInput traineeDtoInput);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "user.isActive")
    TraineeDtoOutput toDtoOutput(Trainee trainee);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.isActive", source = "isActive")
    void updateTraineeProfile(@MappingTarget Trainee existingTrainee, TraineeProfileDtoInput traineeDtoInput);

    TraineeUpdateListDtoOutput toTraineeUpdateListDtoOutput(Trainee trainee);

    @Mapping(target = "username", expression = "java(appendPostfix(trainee.getUser()))")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    TraineeForTrainerDtoOutput toTraineeForTrainerDtoOutput(Trainee trainee);
    default String appendPostfix(User user) {
        return (user.getPostfix() != 0) ? user.getUsername() + "-" + user.getPostfix() : user.getUsername();
    }
}