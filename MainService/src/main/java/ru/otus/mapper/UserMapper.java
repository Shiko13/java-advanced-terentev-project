package ru.otus.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.model.User;
import ru.otus.model.dto.UserWithPassword;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", source = "encodedPassword")
    User toEntity(UserWithPassword userWithPassword);
}
