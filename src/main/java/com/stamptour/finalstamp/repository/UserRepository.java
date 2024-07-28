package com.stamptour.finalstamp.repository;

import com.stamptour.finalstamp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    Optional<User> findByToken(String token);
    List<User> findByCreatedAtBefore(LocalDateTime dateTime);
}
