package ru.otus.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoInput {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
