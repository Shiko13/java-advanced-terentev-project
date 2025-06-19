package ru.otus.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.mapper.TrainerMapper;
import ru.otus.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainerMapperTest {

    private final TrainerMapper trainerMapper = new TrainerMapperImpl();

    @Test
    void appendPostfixWithNonZeroPostfix_ShouldOk() {
        User user = new User();
        user.setUsername("testUser");
        user.setPostfix(1);

        String result = trainerMapper.appendPostfix(user);

        assertEquals("testUser-1", result);
    }

    @Test
    void appendPostfixWithZeroPostfix_ShouldOk() {
        User user = new User();
        user.setUsername("testUser");
        user.setPostfix(0);

        String result = trainerMapper.appendPostfix(user);

        assertEquals("testUser", result);
    }
}
