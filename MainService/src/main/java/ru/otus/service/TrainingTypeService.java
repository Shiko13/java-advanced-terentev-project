package ru.otus.service;

import ru.otus.model.dto.TrainingTypeOutputDto;

import java.util.List;

public interface TrainingTypeService {

    List<TrainingTypeOutputDto> getAll();
}
