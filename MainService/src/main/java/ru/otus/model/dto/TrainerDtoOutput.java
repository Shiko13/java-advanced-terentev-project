package ru.otus.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDtoOutput {

    private String firstName;

    private String lastName;

    private TrainingTypeShortOutputDto specialization;

    @JsonProperty("isActive")
    private Boolean isActive;

    private List<TraineeForTrainerDtoOutput> trainees;
}