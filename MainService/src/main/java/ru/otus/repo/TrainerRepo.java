package ru.otus.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepo extends JpaRepository<Trainer, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "trainer-with-users-training-type-and-trainees-graph")
    Optional<Trainer> findByUserId(Long userId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "trainer-with-users-training-type-and-trainees-graph")
    List<Trainer> findByTraineesIsEmptyAndUserIsActiveTrue();

    List<Trainer> findAllByUser_UsernameIn(List<String> usernames);

    Optional<Trainer> findByUser_Username(String username);
}
