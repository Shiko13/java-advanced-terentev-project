package ru.otus.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.model.TrainingType;

public interface TrainingTypeRepo extends JpaRepository<TrainingType, Long> {
}
