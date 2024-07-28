package com.stamptour.finalstamp.controller;

import com.stamptour.finalstamp.domain.User;
import com.stamptour.finalstamp.dto.StampRequest;
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

    @GetMapping("/user-info")
    public ResponseEntity<UserResponseDto> getUserInfo(@RequestHeader("Authorization") String token) {
        logger.warn("- user-info request received");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.isTokenValid(token)) {
            logger.warn("# invalid token(stamp)");
            return ResponseEntity.status(401).build();
        }

        String userid = jwtUtil.extractUsername(token);
        logger.info("** user-info request for user ID: " + userid);

        User user = userService.findByToken(token);

        UserResponseDto responseDto = new UserResponseDto(user);
        responseDto.setToken(token);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/save-stamp")
    public ResponseEntity<UserResponseDto> updateData(@RequestHeader("Authorization") String token, @RequestBody StampRequest stampRequest) {
        logger.warn("- stamp save start");
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtil.isTokenValid(token)) {
            logger.warn("# invalid token");
            return ResponseEntity.status(401).build();
        }

        String userid = jwtUtil.extractUsername(token);
        logger.info("** stamp update user ID: " + userid);

        Optional<User> userOptional = userService.findByUserid(userid);

        if (userOptional.isEmpty()) {
            logger.warn("# not found user ID: " + userid);
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
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
                logger.error("$ QR param is error: " + id);
                return ResponseEntity.badRequest().build();
        }

        // QR 코드 업데이트 저장
        userService.saveQrstamp(user);

        UserResponseDto responseDto = new UserResponseDto(user);
        logger.info("** user update completed: " + responseDto);

        return ResponseEntity.ok(responseDto);
    }
}
