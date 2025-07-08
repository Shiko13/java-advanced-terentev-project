package ru.otus.mapper;

import org.mapstruct.Mapper;
import ru.otus.model.TrainingType;
import ru.otus.model.dto.TrainingTypeOutputDto;
import ru.otus.model.dto.TrainingTypeShortOutputDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {

    TrainingTypeShortOutputDto toShortDto(TrainingType trainingType);

    TrainingTypeOutputDto toDto(TrainingType trainingType);

    List<TrainingTypeOutputDto> toDtoList(List<TrainingType> trainingTypes);
}
