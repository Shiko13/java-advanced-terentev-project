package ru.otus.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.model.Trainee;

import java.util.Optional;

public interface TraineeRepo extends JpaRepository<Trainee, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "trainee-with-users-and-trainers-graph")
    Optional<Trainee> findByUserId(Long userId);

    Optional<Trainee> findByUser_Username(String username);
}
