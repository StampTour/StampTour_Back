package com.stamptour.backend.controller;

import com.stamptour.backend.repository.UserRepository;
import com.stamptour.backend.request.UserRequest;
import com.stamptour.backend.user.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository; // UserRepository는 사용자 정보를 관리하는 Repository

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<String> loginOrRegister(@RequestBody UserRequest userRequest, HttpSession session) {
        String inputUserid = userRequest.getUserid();
        String sessionUserid = (String) session.getAttribute("userid");

        logger.info("Received login request for userid: " + inputUserid);
        logger.info("Current session userid: " + sessionUserid);

        if (sessionUserid != null) {
            User sessionUser = userRepository.findByUserid(sessionUserid);

            if (sessionUser != null && sessionUser.getUserid().equals(inputUserid)) {
                logger.info("세션 유효: " + sessionUserid);
                return new ResponseEntity<>("로그인 성공: " + sessionUserid, HttpStatus.OK);
            } else {
                logger.info("로그인 실패: 세션 정보와 일치하지 않습니다");
                return new ResponseEntity<>("로그인 실패: 세션 정보와 일치하지 않습니다", HttpStatus.UNAUTHORIZED);
            }
        } else {
            User user = userRepository.findByUserid(inputUserid);

            if (user == null) {
                // 사용자가 존재하지 않으면 회원가입
                User newUser = new User(inputUserid);
                userRepository.save(newUser);
                session.setAttribute("userid", inputUserid);
                logger.info("회원가입 및 로그인 성공: 세션에 저장된 userid = " + session.getAttribute("userid"));
                return new ResponseEntity<>("회원가입 및 로그인 성공", HttpStatus.OK);
            } else {
                // 사용자가 존재하면 로그인
                session.setAttribute("userid", inputUserid);
                logger.info("로그인 성공: 세션에 저장된 userid = " + session.getAttribute("userid"));
                return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
            }
        }
    }

    @GetMapping("/session-check")
    public ResponseEntity<String> checkSession(HttpSession session) {
        String userid = (String) session.getAttribute("userid");

        logger.info("Checking session. Current session userid: " + userid);

        if (userid != null) {
            User user = userRepository.findByUserid(userid);
            if (user != null) {
                logger.info("세션 유효: " + userid);
                return new ResponseEntity<>("세션 유효: " + userid, HttpStatus.OK);
            } else {
                session.invalidate();
                logger.info("세션이 유효하지 않습니다: 사용자 정보 없음");
                return new ResponseEntity<>("세션이 유효하지 않습니다: 사용자 정보 없음", HttpStatus.UNAUTHORIZED);
            }
        } else {
            logger.info("세션이 유효하지 않습니다");
            return new ResponseEntity<>("세션이 유효하지 않습니다", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/session-info")
    public ResponseEntity<String> sessionInfo(HttpSession session) {
        String sessionUserid = (String) session.getAttribute("userid");
        logger.info("Current session userid: " + sessionUserid);

        if (sessionUserid != null) {
            return new ResponseEntity<>("세션에 저장된 userid: " + sessionUserid, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("세션에 userid가 없습니다", HttpStatus.OK);
        }
    }
}
