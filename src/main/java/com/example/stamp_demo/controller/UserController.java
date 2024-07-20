package com.example.stamp_demo.controller;

import com.example.stamp_demo.domain.QrStamp;
import com.example.stamp_demo.domain.QrStampId;
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
        String inputUserid = user.getUserid();
        String sessionUserid = (String) session.getAttribute("userid");

        logger.info("Received login request for userid: " + inputUserid);
        logger.info("Current session userid: " + sessionUserid);

        if (sessionUserid != null && sessionUserid.equals(inputUserid)) {
            logger.info("세션 유효: " + sessionUserid);
            return new ResponseEntity<>("로그인 진행: " + sessionUserid, HttpStatus.OK);
        } else {
            user = userRepository.findByUserid(inputUserid);

            if (user == null) {
                // 사용자가 존재하지 않으면 회원가입
                User newUser = new User(inputUserid);
                userRepository.save(newUser);
                session.setAttribute("userid", inputUserid);
                session.setAttribute("id", newUser.getId());
                QrStampId qrStampId = new QrStampId();
                qrStampId.setQrId(1L); // qrId를 적절히 설정
                qrStampId.setUsr(newUser.getId());
                QrStamp qrStamp = new QrStamp(qrStampId, newUser);
                qrService.saveQrStamp(qrStamp);
                logger.info("회원가입 및 로그인 성공: 세션에 저장된 userid = " + session.getAttribute("userid"));
                return new ResponseEntity<>("회원가입 및 로그인 성공", HttpStatus.OK);
            } else {
                // 사용자가 존재하면 로그인
                session.setAttribute("userid", inputUserid);
                session.setAttribute("id", user.getId());
                logger.info("로그인 성공: 세션에 저장된 userid = " + session.getAttribute("userid"));
                return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
            }
        }
    }
}
