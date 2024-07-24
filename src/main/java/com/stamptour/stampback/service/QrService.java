package com.stamptour.stampback.service;


import com.stamptour.stampback.domain.QrStamp;
import com.stamptour.stampback.repository.QrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QrService {

    private final QrRepository qrRepository;

    @Autowired
    public QrService(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    // 특정 User의 id로 Qr 엔티티 조회
    public Optional<QrStamp> findByUsrid(String usrid) {
        return qrRepository.findByUsrid(usrid); // 메서드명 수정
    }

    public QrStamp saveQrStamp(QrStamp qrstamp) {
        return qrRepository.save(qrstamp);
    }
}
