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

        if (sessionUserid != null && sessionUserid.equals(inputUserid)) {
            logger.info("세션 유효: " + sessionUserid);
            return new ResponseEntity<>("로그인 성공: " + sessionUserid, HttpStatus.OK);
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

}
