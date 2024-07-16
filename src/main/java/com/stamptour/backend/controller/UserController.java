package com.stamptour.backend.controller;

import com.stamptour.backend.repository.UserRepository;
import com.stamptour.backend.user.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository; // UserRepository는 사용자 정보를 관리하는 Repository

    @PostMapping("/login")
    public ResponseEntity<String> loginOrRegister(@RequestBody UserRequest userRequest, HttpSession session) {
        String userid = userRequest.getUserid();
        User user = userRepository.findByUserid(userid);

        if (user == null) {
            // 사용자가 존재하지 않으면 회원가입
            User newUser = new User(userid);
            userRepository.save(newUser);
            session.setAttribute("userid", userid);
            return new ResponseEntity<>("회원가입 및 로그인 성공", HttpStatus.OK);
        } else {
            // 사용자가 존재하면 로그인
            session.setAttribute("userid", userid);
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        }
    }

    // 세션에 저장된 사용자 정보 확인
    @GetMapping("/session-check")
    public ResponseEntity<String> checkSession(HttpSession session) {
        String userid = (String) session.getAttribute("userid");

        if (userid != null) {
            return new ResponseEntity<>("세션 유효: " + userid, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("세션이 유효하지 않습니다", HttpStatus.UNAUTHORIZED);
        }
    }

    // UserRequest 클래스는 클라이언트에서 보내는 데이터 구조를 정의한 클래스
    static class UserRequest {
        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
