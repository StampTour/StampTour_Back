package com.example.stamp_demo.repository;


import com.example.stamp_demo.domain.QrStamp;
import com.example.stamp_demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrRepository extends JpaRepository<QrStamp, Long> {

    Optional<QrStamp> findByUserId(Long userId);
}
