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

    @GetMapping("/userinfologin")
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
        // 로그인 및 사용자 등록 요청에서 사용자 정보와 스탬프 요청을 추출합니다.
        UserRequest userRequest = loginRequest.getUserRequest();
        StampRequest stampRequest = loginRequest.getStampRequest();

        // 요청으로부터 사용자 ID를 가져옵니다.
        String inputUserUserid = userRequest.getUserid();
        logger.info("** Received login request for userid: " + inputUserUserid);

        // 사용자 ID로 데이터베이스에서 사용자를 검색합니다.
        Optional<User> userOptional = userService.findByUserid(inputUserUserid);

        User user;
        if (userOptional.isEmpty()) {
            // 데이터베이스에 사용자가 존재하지 않는 경우, 새로운 사용자로 등록합니다.
            logger.info("* User information does not exist in the database. New user registration required: " + inputUserUserid);
            user = new User(inputUserUserid);
            userService.registerUser(user);
            logger.info("** register userid completed: " + inputUserUserid);
        } else {
            // 데이터베이스에 사용자가 존재하는 경우, 해당 사용자 객체를 가져옵니다.
            user = userOptional.get();
            logger.info("** login userid completed: " + user.getUserid());
        }

        // 스탬프 요청이 있는 경우, QR 코드 관련 플래그를 업데이트합니다.
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
                    // 유효하지 않은 QR 코드 ID인 경우 경고 로그를 기록합니다.
                    logger.warn("$ QR param is nope: " + id);
            }

            // QR 코드 업데이트 후 사용자 정보를 데이터베이스에 저장합니다.
            userService.saveQrstamp(user);
        }

        // 사용자 ID로 JWT 토큰을 생성합니다.
        String newToken = jwtUtil.generateToken(user.getUserid());

        // 생성된 JWT 토큰을 사용자 객체에 저장합니다.
        userService.saveUserToken(user, newToken);

        // UserResponseDto를 생성하여 사용자 정보와 JWT 토큰을 포함시킵니다.
        UserResponseDto responseDto = new UserResponseDto(user);
        responseDto.setToken(newToken);

        // 응답으로 UserResponseDto 객체를 반환합니다.
        return ResponseEntity.ok(responseDto);
    }

}










