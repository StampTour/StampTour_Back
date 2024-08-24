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

    // UserService 를 통해 사용자 데이터에 접근합니다.
    @Autowired
    private UserService userService;

    // 로깅을 위한 Logger 객체를 생성합니다.
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 사용자의 로그인 또는 회원가입을 처리합니다.
     * 클라이언트의 로그인 요청을 받아, 사용자 정보가 있으면 로그인, 없으면 회원가입을 진행합니다.
     * 또한, 스탬프 요청이 있는 경우 해당 정보를 업데이트합니다.
     *
     * @param loginRequest 클라이언트로부터 받은 로그인 요청 데이터 (사용자 정보와 스탬프 정보 포함)
     * @param session 현재 세션 객체
     * @return 로그인 또는 회원가입 후의 사용자 정보를 담은 응답 객체
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginOrRegister(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // 로그인 요청에서 사용자 정보와 스탬프 정보를 추출합니다.
        UserRequest userRequest = loginRequest.getUserRequest();
        StampRequest stampRequest = loginRequest.getStampRequest();

        // 입력된 사용자 ID를 가져옵니다.
        String inputUserUserid = userRequest.getUserid();
        logger.info("User Controller - ** Received login request for userid: {}", inputUserUserid);

        // 데이터베이스에서 사용자 ID로 사용자를 조회합니다.
        Optional<User> userOptional = userService.findByUserid(inputUserUserid);

        User user;
        if (userOptional.isEmpty()) {
            // 사용자가 존재하지 않으면, 새로운 사용자를 등록합니다.
            user = new User(inputUserUserid);
            userService.registerUser(user);
            logger.info("User Controller - ** register userid completed: {}", inputUserUserid);
        } else {
            // 사용자가 이미 존재하면, 해당 사용자 정보를 가져옵니다.
            user = userOptional.get();
            logger.info("User Controller - ** login userid completed: {}", user.getUserid());
        }

        // 세션 ID를 생성하여 사용자 객체에 저장합니다.
        String sessionId = session.getId();
        user.setSessionId(sessionId);
        userService.saveSession(user, sessionId);  // 사용자 정보를 업데이트하여 세션 ID를 저장합니다.

        // 스탬프 요청이 있는 경우, 해당 QR 코드와 관련된 필드를 업데이트합니다.
        if (stampRequest != null) {
            int id = stampRequest.getId();
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

            // 스탬프 정보를 데이터베이스에 저장합니다.
            userService.saveQrstamp(user);
        }

        // 세션에 사용자 정보를 저장합니다.
        session.setAttribute("user", user);

        // 사용자 정보를 담은 응답 객체를 생성합니다.
        UserResponseDto responseDto = new UserResponseDto(user);

        // 사용자 정보를 클라이언트에 반환합니다.
        return ResponseEntity.ok(responseDto);
    }
}
