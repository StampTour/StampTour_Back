package com.stamptour.backend.controller;


import com.stamptour.backend.repository.UserRepository;
import com.stamptour.backend.domain.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository; // UserRepository는 사용자 정보를 관리하는 Repository


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpSession session) {
        String inputPassword = user.getPassword();

        // 입력된 비밀번호로 사용자 검색
        User existingUser = userRepository.findByPassword(inputPassword);
        if (existingUser != null) {
            // 사용자 인증 성공: 세션에 사용자 ID 저장
            session.setAttribute("password", existingUser.getPassword()); // 세션에 사용자 ID 저장

            // 로그인 성공 시, 클라이언트에게 리다이렉트 URL 반환
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "https://tmdstamptour.netlify.app/"); // React 애플리케이션의 URL
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // 사용자 인증 실패: 잘못된 비밀번호일 경우, 클라이언트에게 인증 실패 알림
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user, HttpSession session) {
        String inputPassword = user.getPassword();

        // 입력된 비밀번호로 사용자 검색
        User existingUser = userRepository.findByPassword(inputPassword);

        // 사용자가 존재하지 않으면 회원가입
        if (existingUser == null) {
            // 사용자가 존재하지 않으면 새 사용자 생성 및 저장
            User newUser = new User(inputPassword);
            userRepository.save(newUser);

            // 회원가입 성공 후 세션에 새 사용자 ID 저장
            session.setAttribute("password", newUser.getPassword()); // 세션에 사용자 ID 저장

            // 회원가입 성공 시, 클라이언트에게 리다이렉트 URL 반환
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "https://tmdstamptour.netlify.app/"); // React 애플리케이션의 URL
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // 사용자 인증 실패: 잘못된 비밀번호일 경우, 클라이언트에게 인증 실패 알림
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}