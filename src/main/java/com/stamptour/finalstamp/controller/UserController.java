package com.stamptour.finalstamp.controller;

import com.stamptour.finalstamp.domain.User;
import com.stamptour.finalstamp.dto.*;
import com.stamptour.finalstamp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 로그인 또는 회원가입을 처리하고, 스탬프 정보가 있으면 업데이트합니다.
     * 클라이언트의 로그인 요청을 받아 사용자 정보를 확인하고 세션을 생성합니다.
     *
     * @param loginRequest 클라이언트로부터 받은 로그인 요청 데이터
     * @param session 현재 세션 객체
     * @return 로그인 또는 회원가입 후의 사용자 정보를 담은 응답 객체
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginOrRegister(@RequestBody LoginRequest loginRequest, HttpSession session) {
        UserRequest userRequest = loginRequest.getUserRequest(); // 로그인 사용자 정보
        StampRequest stampRequest = loginRequest.getStampRequest(); // 스탬프 요청 정보

        String inputUserUserid = userRequest.getUserid();
        logger.info("User Controller - ** Received login request for userid: {}", inputUserUserid);

        // 사용자 ID로 데이터베이스에서 사용자 조회
        Optional<User> userOptional = userService.findByUserid(inputUserUserid);

        User user;
        if (userOptional.isEmpty()) {
            // 사용자가 존재하지 않으면 새로운 사용자 등록
            user = new User(inputUserUserid);
            userService.registerUser(user);
            logger.info("User Controller - ** register userid completed: {}", inputUserUserid);
        } else {
            // 사용자가 이미 존재하면 해당 사용자 정보 가져오기
            user = userOptional.get();
            logger.info("User Controller - ** login userid completed: {}", user.getUserid());
        }

        // 세션 ID를 생성하여 사용자 객체에 저장합니다.
        String sessionId = session.getId();
        user.setSessionId(sessionId);
        userService.saveSession(user, sessionId);  // 사용자 정보를 업데이트하여 세션 ID를 저장합니다.

        session.setAttribute("user", user); // 세션에 사용자 정보를 저장합니다.
        logger.info("User Controller - saveSession: {}, {}", user, sessionId);

        // 스탬프 요청이 있는 경우, 해당 QR 코드 업데이트
        if (stampRequest != null) {
            Integer id = stampRequest.getId();
            switch (id) {
                case 1 -> user.setQr1(true);
                case 2 -> user.setQr2(true);
                case 3 -> user.setQr3(true);
                case 4 -> user.setQr4(true);
                case 5 -> user.setQr5(true);
                case 6 -> user.setQr6(true);
                case 7 -> user.setQr7(true);
                case 8 -> user.setQr8(true);
                case 9 -> user.setQr9(true);
                case 10 -> user.setQr10(true);
                default -> logger.warn("QR param is warning: {}", id);
            }
            userService.saveQrstamp(user); // 스탬프 정보 데이터베이스에 저장
        }

        logger.info("Login : {}", sessionId);
        // 사용자 정보를 응답 객체에 담아 반환
        UserResponseDto responseDto = new UserResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }
}

