package com.stamptour.finalstamp.repository;

import com.stamptour.finalstamp.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    Optional<User> findBySessionId(String sessionId);

    // ^^ 특정 날짜 및 시간 이전의 사용자 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.createdAt <= :dateTime")
    void deleteUserAndTokens(LocalDateTime dateTime);
}
