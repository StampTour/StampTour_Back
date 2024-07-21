package com.example.stamp_demo.service;

import com.example.stamp_demo.domain.QrStamp;
import com.example.stamp_demo.repository.QrRepository;
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

    // 특정 User의 pw로 Qr 엔티티 조회
    public Optional<QrStamp> findByUsrpw(String usrpw) {
        return qrRepository.findByUsrpw(usrpw); // 메서드명 수정
    }

    public QrStamp saveQrStamp(QrStamp qrstamp) {
        return qrRepository.save(qrstamp);
    }
}
