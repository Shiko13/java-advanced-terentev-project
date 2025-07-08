package ru.otus.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingForTrainerDtoOutput {

    private String name;

    private LocalDate date;

    private String type;

    private Long duration;

    private String traineeName;
}
