package com.stamptour.stampback.controller;


import com.stamptour.stampback.domain.User;
import com.stamptour.stampback.dto.UserResponseDto;
import com.stamptour.stampback.repository.UserRepository;
import com.stamptour.stampback.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StampController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(StampController.class);

    @PostMapping("/updateData/{param}")
    public ResponseEntity<UserResponseDto> updateData(@PathVariable int param, HttpSession session) {
        // 세션에서 사용자 ID를 가져옵니다.
        String sessionUserUserid = (String) session.getAttribute("userid");
        logger.info("스탬프 업데이트 요청을 받은 사용자 ID: " + sessionUserUserid);

        // 사용자 ID로 데이터베이스에서 사용자 정보를 조회합니다.
        Optional<User> userOptional = userService.findByUserid(sessionUserUserid);

        // 사용자가 데이터베이스에 존재하지 않는 경우
        if (userOptional.isEmpty()) {
            logger.warn("데이터베이스에서 사용자 ID를 찾을 수 없습니다: " + sessionUserUserid);
            return ResponseEntity.notFound().build();
        }

        // 사용자 정보를 가져옵니다.
        User user = userOptional.get();

        // param 값에 따라 QR 상태를 업데이트합니다.
        switch (param) {
            case 1:
                user.setQr1(true);
                break;
            case 2:
                user.setQr2(true);
                break;
            case 3:
                user.setQr3(true);
                break;
            case 4:
                user.setQr4(true);
                break;
            case 5:
                user.setQr5(true);
                break;
            case 6:
                user.setQr6(true);
                break;
            case 7:
                user.setQr7(true);
                break;
            case 8:
                user.setQr8(true);
                break;
            case 9:
                user.setQr9(true);
                break;
            case 10:
                user.setQr10(true);
                break;
            default:
                logger.error("잘못된 QR 파라미터 값: " + param);
                return ResponseEntity.badRequest().build();
        }

        // 수정된 사용자 정보를 데이터베이스에 저장합니다.
        userService.saveQrstamp(user);

        // 사용자 응답 DTO를 생성하고 반환합니다.
        UserResponseDto responseDto = new UserResponseDto(user);
        logger.info("사용자 정보 업데이트 완료: " + responseDto);

        return ResponseEntity.ok(responseDto);
    }
}

