package com.stamptour.finalstamp.controller;

import com.stamptour.finalstamp.domain.User;
import com.stamptour.finalstamp.dto.UserResponseDto;
import com.stamptour.finalstamp.service.UserService;
import com.stamptour.finalstamp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class StampController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(StampController.class);

    /**
     * 세션에서 사용자 정보를 가져와 사용자 정보를 반환합니다.
     * 세션에 사용자가 없으면 401 상태 코드를 반환합니다.
     *
     * @param session 현재 세션 객체
     * @return 사용자 정보를 담은 응답 객체 또는 인증 실패 시 401 상태 코드 반환
     */
    @GetMapping("/userinfo")
    public ResponseEntity<UserResponseDto> getUserInfo(HttpSession session) {
        logger.info("StampController - user-info request received");

        // 세션에서 사용자 정보 가져오기
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 세션에 사용자가 없을 경우, 401 상태 코드를 반환
            logger.info("StampController userinfo - user is null");
            return ResponseEntity.status(401).build();
        }

        logger.info("StampController - user-info request for user ID: {}", user.getUserid());

        // 데이터베이스에서 최신 사용자 정보 조회
        User updatedUser = userRepository.findByUserid(user.getUserid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 최신 사용자 정보를 응답 객체에 담아 반환
        UserResponseDto responseDto = new UserResponseDto(updatedUser);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * QR 코드 정보를 업데이트하고 세션에 저장된 사용자 정보를 반환합니다.
     *
     * @param stampedId 업데이트할 QR 코드 ID
     * @param session 현재 세션 객체
     * @return 업데이트된 사용자 정보를 담은 응답 객체 또는 인증 실패 시 401 상태 코드 반환
     */
    @PostMapping("/savestamp")
    public ResponseEntity<UserResponseDto> updateQrInfo(@RequestParam("stampedId") int stampedId, HttpSession session) {
        logger.info("StampController - update QR info request received");

        // 세션에서 사용자 정보 가져오기
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 세션에 사용자가 없을 경우, 401 상태 코드를 반환
            logger.warn("StampController save stamp - user is null");
            return ResponseEntity.status(401).build();
        }

        logger.info("StampController - update QR info request for user ID: {}, stampedId: {}", user.getUserid(), stampedId);

        try {
            // QR 코드 상태 업데이트
            userService.updateQrFlag(user.getUserid(), stampedId);

            // 업데이트된 사용자 정보를 데이터베이스에서 다시 조회
            User updatedUser = userRepository.findByUserid(user.getUserid())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 세션에 업데이트된 사용자 정보 저장 (선택 사항)
            session.setAttribute("user", updatedUser);

            // 업데이트된 사용자 정보를 응답 객체에 담아 반환
            UserResponseDto responseDto = new UserResponseDto(updatedUser);
            return ResponseEntity.ok(responseDto);

        } catch (Exception e) {
            // 예외를 로깅하고, 클라이언트에게 적절한 응답 반환
            logger.error("Error updating QR flag for user ID: {} with stampedId: {}", user.getUserid(), stampedId, e);
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }
}
