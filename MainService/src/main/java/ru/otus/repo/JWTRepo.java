package ru.otus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.JWT;

public interface JWTRepo extends MongoRepository<JWT, String> {
    boolean existsByToken(String token);
}
