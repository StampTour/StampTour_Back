package com.stamptour.stampback.controller;


import com.stamptour.stampback.domain.QrStamp;
import com.stamptour.stampback.service.QrService;

import com.stamptour.stampback.domain.User;
import com.stamptour.stampback.repository.UserRepository;
import com.stamptour.stampback.dto.UserResponseDto;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = {"http://localhost:63342",
        "http://localhost:3000",
        "https://tmdstamptour.netlify.app",
        "https://tmdstamptour.netlify.app/"})
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository; // UserRepository는 사용자 정보를 관리하는 Repository
    @Autowired
    private QrService qrService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginOrRegister(@RequestBody User user, HttpSession session) {
        // 클라이언트에서 보낸 요청의 본문을 User 객체로 변환합니다.
        String inputUserUserid = user.getUserid();
        String sessionUserUserid = (String) session.getAttribute("userid");

        logger.info("Received login request for userid: " + inputUserUserid);
        logger.info("Current session userid: " + sessionUserUserid);

        // 세션에 저장된 userId가 있고, 입력된 userId와 일치하는지 확인합니다.
        if (sessionUserUserid != null && sessionUserUserid.equals(inputUserUserid)) {
            logger.info("세션 유효: " + sessionUserUserid);
            return new ResponseEntity<>(new UserResponseDto(sessionUserUserid, "로그인 진행: " + sessionUserUserid), HttpStatus.OK); // 세션 유효 시 로그인 진행
        } else {
            user = userRepository.findByUserid(inputUserUserid);

            if (user == null) {
                // 사용자가 존재하지 않으면 회원가입 진행
                User newUser = new User(inputUserUserid);
                userRepository.save(newUser);
                session.setAttribute("userid", newUser.getUserid());
                QrStamp qrStamp = QrStamp.builder()
                        .user(user)
                        .build();
                qrService.saveQrStamp(qrStamp);

                logger.info("회원가입 및 로그인 성공: 세션에 저장된 userid = " + session.getAttribute("userid"));
                // 회원가입 성공 시 응답
                return new ResponseEntity<>(new UserResponseDto(newUser.getUserid(), "회원가입 및 로그인 성공"), HttpStatus.OK);
            } else {
                // 사용자가 존재하면 로그인 진행
                session.setAttribute("userid", user.getUserid());
                logger.info("로그인 성공: 세션에 저장된 userid = " + session.getAttribute("userid"));
                // 로그인 성공 시 응답
                return new ResponseEntity<>(new UserResponseDto(user.getUserid(), "로그인 성공"), HttpStatus.OK);
            }
        }
    }
}

