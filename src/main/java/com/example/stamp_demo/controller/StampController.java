package com.example.stamp_demo.controller;

import com.example.stamp_demo.dto.QrResponseDto;
import com.example.stamp_demo.repository.UserRepository;
import com.example.stamp_demo.domain.QrStamp;

import com.example.stamp_demo.service.QrService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api/stamp")
public class StampController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QrService qrService;

    private static final Logger logger = LoggerFactory.getLogger(StampController.class);

    @PutMapping("/updateData/{param}")
    public ResponseEntity<QrResponseDto> updateData(@PathVariable int param, @RequestBody QrStamp qrstamp, HttpSession session) {
        String sessionUserPassword = (String) session.getAttribute("password");

        // 세션에서 가져온 사용자 pw로 Qr 엔티티 조회
        Optional<QrStamp> qrOptional = qrService.findByUsrpw(sessionUserPassword);

        if (qrOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        QrStamp qr = qrOptional.get();
        // param 값에 따라 QR 데이터 수정
        switch (param) {
            case 1:
                qr.setQr1(true);
                break;
            case 2:
                qr.setQr2(true);
                break;
            case 3:
                qr.setQr3(true);
                break;
            case 4:
                qr.setQr4(true);
                break;
            case 5:
                qr.setQr5(true);
                break;
            case 6:
                qr.setQr6(true);
                break;
            case 7:
                qr.setQr7(true);
                break;
            case 8:
                qr.setQr8(true);
                break;
            case 9:
                qr.setQr9(true);
                break;
            case 10:
                qr.setQr10(true);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        // 수정된 QR 엔티티 저장
        qrService.saveQrStamp(qr);

        // QrResponseDto 생성 및 반환
        QrResponseDto responseDto = new QrResponseDto(qr);

        return ResponseEntity.ok(responseDto);
    }
}
