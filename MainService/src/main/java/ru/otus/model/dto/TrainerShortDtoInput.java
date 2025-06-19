package ru.otus.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainerShortDtoInput {

    @NotBlank
    private String username;
}
