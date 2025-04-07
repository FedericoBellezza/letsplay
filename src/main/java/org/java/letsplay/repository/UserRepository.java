package org.java.letsplay.repository;

import java.util.Optional;

import org.java.letsplay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
