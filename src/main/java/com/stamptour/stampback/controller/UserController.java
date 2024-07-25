package com.stamptour.stampback.controller;


import com.stamptour.stampback.domain.User;
import com.stamptour.stampback.dto.UserRequest;
import com.stamptour.stampback.dto.UserResponseDto;

import com.stamptour.stampback.service.UserService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;  // UserService를 사용해 사용자 관련 처리를 합니다.

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginOrRegister(@RequestBody UserRequest userRequest, HttpSession session) {
        // 클라이언트에서 보낸 요청의 본문을 UserRequest 객체로 변환합니다.
        String inputUserUserid = userRequest.getUserid();
        String sessionUserid = (String) session.getAttribute("userid");

        logger.info("Received login request for userid: " + inputUserUserid);
        logger.info("Current session userid: " + sessionUserid);

        // 데이터베이스에서 입력된 사용자 ID로 사용자 정보를 조회합니다.
        Optional<User> userOptional = userService.findByUserid(inputUserUserid);

        // 세션에서 가져온 사용자 ID와 데이터베이스의 사용자 정보를 비교합니다.
        if (sessionUserid != null && sessionUserid.equals(inputUserUserid)) {
            // 세션에 저장된 사용자 ID와 입력된 ID가 일치하는 경우, 로그인 진행
            logger.info("세션 유효: " + sessionUserid);
            Optional<User> sessionUserOptional = userService.findByUserid(sessionUserid);

            // 데이터베이스에서 세션 사용자 정보를 찾습니다.
            if (sessionUserOptional.isPresent()) {
                User user = sessionUserOptional.get();
                logger.info("로그인 성공: " + user.getUserid());
                // 사용자 정보를 담은 DTO를 반환
                UserResponseDto responseDto = new UserResponseDto(user);
                return ResponseEntity.ok(responseDto);
            } else {
                // 세션 사용자 ID로 데이터베이스에서 사용자 정보를 찾을 수 없는 경우
                logger.warn("세션 사용자 ID로 데이터베이스에서 사용자 정보를 찾을 수 없음: " + sessionUserid);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            // 세션에 저장된 사용자 ID와 입력된 ID가 일치하지 않거나 세션이 없는 경우
            if (userOptional.isEmpty()) {
                // 데이터베이스에 사용자 정보가 없는 경우, 새 사용자 등록
                logger.info("데이터베이스에 사용자 정보가 존재하지 않음. 새로운 사용자 등록 필요: " + inputUserUserid);
                User newUser = new User(inputUserUserid);  // 새로운 사용자 객체 생성
                userService.registerUser(newUser);  // 사용자 정보를 데이터베이스에 저장
                session.setAttribute("userid", inputUserUserid);  // 세션에 사용자 ID 저장

                // 사용자 정보를 담은 DTO를 반환
                UserResponseDto responseDto = new UserResponseDto(newUser);
                logger.info("회원가입 성공: " + inputUserUserid);
                return ResponseEntity.ok(responseDto);
            } else {
                // 데이터베이스에서 사용자 정보를 찾은 경우
                User user = userOptional.get();
                session.setAttribute("userid", inputUserUserid);  // 세션에 사용자 ID 저장
                logger.info("로그인 성공: " + user.getUserid());

                // 사용자 정보를 담은 DTO를 반환
                UserResponseDto responseDto = new UserResponseDto(user);
                return ResponseEntity.ok(responseDto);
            }
        }
    }
}






