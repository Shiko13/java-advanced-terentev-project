package ru.otus.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT MAX(u.postfix) FROM User u WHERE u.username = :username")
    Integer findMaxPostfixByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndPostfix(String username, Integer postfix);
}
