package com.stamptour.finalstamp.controller;

import com.stamptour.finalstamp.domain.User;
import com.stamptour.finalstamp.dto.UserResponseDto;
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
public class StampController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(StampController.class);

    @GetMapping("/userinfo")
    public ResponseEntity<UserResponseDto> getUserInfo(@RequestHeader("Authorization") String token) {
        logger.warn("StampController - user-info request received");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.isTokenValid(token)) {
            logger.warn("StampController -# invalid token(stamp)");
            return ResponseEntity.status(401).build();
        }

        String userid = jwtUtil.extractUsername(token);
        logger.info("StampController - ** user-info request for user ID: {}", userid);

        User user = userService.findByUserid(userid).orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("StampController - ** user-info findByToken check: {}", user);

        UserResponseDto responseDto = new UserResponseDto(user);
        responseDto.setToken(token);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/savestamp")
    public ResponseEntity<UserResponseDto> updateQrInfo(@RequestParam("stampedId") int stampedId, @RequestHeader("Authorization") String token) {
        logger.warn("StampController - update QR info request received");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.isTokenValid(token)) {
            logger.warn("StampController - # invalid token");
            return ResponseEntity.status(401).build();
        }

        String userid = jwtUtil.extractUsername(token);
        logger.info("StampController - ** update QR info request for user ID: {}", userid);

        Optional<User> userOptional = userService.findByUserid(userid);

        if (userOptional.isEmpty()) {
            logger.warn("StampController - # not found user ID: {}", userid);
            return ResponseEntity.notFound().build();
        }

        userService.updateQrFlag(userid, stampedId);

        User user = userOptional.get();
        UserResponseDto responseDto = new UserResponseDto(user);
        responseDto.setToken(token);

        return ResponseEntity.ok(responseDto);
    }
}