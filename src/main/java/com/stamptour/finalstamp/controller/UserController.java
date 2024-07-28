package com.stamptour.finalstamp.controller;

import com.stamptour.finalstamp.domain.User;
import com.stamptour.finalstamp.dto.*;
import com.stamptour.finalstamp.service.UserService;
import com.stamptour.finalstamp.util.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user-info-login")
    public ResponseEntity<UserResponseDto> getUserInfo(@RequestHeader("Authorization") String token) {
        logger.warn("- user-info request received");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.isTokenValid(token)) {
            logger.warn("# invalid token(login)");
            return ResponseEntity.status(401).build();
        }

        String userid = jwtUtil.extractUsername(token);
        logger.info("** user-info request for user ID: " + userid);

        User user = userService.findByToken(token);

        UserResponseDto responseDto = new UserResponseDto(user);
        responseDto.setToken(token);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginOrRegister(@RequestBody LoginRequest loginRequest) {
        UserRequest userRequest = loginRequest.getUserRequest();
        StampRequest stampRequest = loginRequest.getStampRequest();

        String inputUserUserid = userRequest.getUserid();
        logger.info("** Received login request for userid: " + inputUserUserid);

        Optional<User> userOptional = userService.findByUserid(inputUserUserid);

        User user;
        if (userOptional.isEmpty()) {
            logger.info("* User information does not exist in the database. New user registration required: " + inputUserUserid);
            user = new User(inputUserUserid);
            userService.registerUser(user);
            logger.info("** register userid completed: " + inputUserUserid);
        } else {
            user = userOptional.get();
            logger.info("** login userid completed: " + user.getUserid());
        }

        // StampRequest 처리 (선택 사항)
        if (stampRequest != null) {
            int id = stampRequest.getId();
            switch (id) {
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
                    logger.error("$ QR param is error: " + id);
            }

            // QR 코드 업데이트 후 사용자 정보 저장
            userService.saveQrstamp(user);
        }

        // JWT 생성
        String newToken = jwtUtil.generateToken(user.getUserid());

        // 토큰을 사용자 객체에 저장
        userService.saveUserToken(user, newToken);

        // UserResponseDto 생성
        UserResponseDto responseDto = new UserResponseDto(user);
        responseDto.setToken(newToken);

        // 응답으로 UserResponseDto 반환
        return ResponseEntity.ok(responseDto);
    }
}










