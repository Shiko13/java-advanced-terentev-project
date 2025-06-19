package ru.otus.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerForTraineeDtoOutput {

    private String username;

    private String firstName;

    private String lastName;

    private TrainingTypeShortOutputDto specialization;
}
