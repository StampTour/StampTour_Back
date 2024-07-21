package com.example.stamp_demo.controller;

import com.example.stamp_demo.domain.QrStamp;
import com.example.stamp_demo.repository.UserRepository;
import com.example.stamp_demo.domain.User;
import com.example.stamp_demo.service.QrService;
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
    @Autowired
    private QrService qrService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<String> loginOrRegister(@RequestBody User user, HttpSession session) {
        String inputUserPassword = user.getPassword();
        String sessionUserPassword = (String) session.getAttribute("password");

        logger.info("Received login request for userid: " + inputUserPassword);
        logger.info("Current session userid: " + sessionUserPassword);

        if ( sessionUserPassword != null && sessionUserPassword.equals(inputUserPassword)) {
            logger.info("세션 유효: " + sessionUserPassword);
            return new ResponseEntity<>("로그인 진행: " + sessionUserPassword, HttpStatus.OK);
        } else {
            user = userRepository.findByPassword(inputUserPassword);

            if (user == null) {
                // 사용자가 존재하지 않으면 회원가입
                User newUser = new User(inputUserPassword);
                userRepository.save(newUser);
                session.setAttribute("password", newUser.getPassword());
                QrStamp qrStamp = QrStamp.builder()
                        .user(user)
                        .build();
                qrService.saveQrStamp(qrStamp);
                logger.info("회원가입 및 로그인 성공: 세션에 저장된 password = " + session.getAttribute("password"));
                return new ResponseEntity<>("회원가입 및 로그인 성공", HttpStatus.OK);
            } else {
                // 사용자가 존재하면 로그인
                session.setAttribute("password", user.getPassword());
                logger.info("로그인 성공: 세션에 저장된 password = " + session.getAttribute("password"));
                return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
            }
        }
    }
}
