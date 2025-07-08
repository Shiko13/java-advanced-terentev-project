package ru.otus.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeUpdateListDtoOutput {

    private List<TrainerForTraineeDtoOutput> trainers;
}
