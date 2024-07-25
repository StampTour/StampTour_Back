package com.stamptour.stampback.repository;

import com.stamptour.stampback.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    List<User> findByCreatedAtBefore(LocalDateTime dateTime);
}
