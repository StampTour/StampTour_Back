package com.stamptour.backend.repository;

import com.stamptour.backend.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByPassword(String password);
}
