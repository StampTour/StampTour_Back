package com.stamptour.stampback.repository;



import com.stamptour.stampback.domain.QrStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrRepository extends JpaRepository<QrStamp, String> {

    Optional<QrStamp> findByUsrid(String usrid);
}
