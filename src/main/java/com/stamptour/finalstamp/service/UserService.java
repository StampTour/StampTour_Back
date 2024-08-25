package com.stamptour.finalstamp.service;

import com.stamptour.finalstamp.controller.StampController;
import com.stamptour.finalstamp.domain.User;
import com.stamptour.finalstamp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Optional;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(StampController.class);

    @Autowired
    private UserRepository userRepository;

    // 새로운 사용자 등록
    public void registerUser(User user) {
        userRepository.save(user);
    }

    // 사용자 ID로 사용자 찾기
    public Optional<User> findByUserid(String userid) {
        return userRepository.findByUserid(userid);
    }

    // QR 상태 업데이트 및 저장
    public void saveQrstamp(User user) {
        logger.info("userService : saveQrStamp start");
        userRepository.save(user);
    }

    // 토큰 저장 메서드 추가
    public void saveSession(User user, String sessionId) {
        logger.info("userService : saveSession");
        user.setSessionId(sessionId);
        userRepository.save(user);
    }

    public void updateQrFlag(String userid, Integer stampedId) {
        User user = userRepository.findByUserid(userid).orElseThrow(() -> new RuntimeException("User not found"));

        switch (stampedId) {
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
                throw new IllegalArgumentException("Invalid stampedId: " + stampedId);
        }

        userRepository.save(user);
    }


    // 정해진 시간에 만료된 사용자와 관련된 토큰 삭제
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 한국 시간 자정에 실행
    public void deleteExpiredUsers() {
        // 한국 시간을 기준으로 2일 전의 날짜와 시간을 구합니다.
        LocalDateTime cutoffDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().minusDays(1);
        userRepository.deleteUserAndTokens(cutoffDateTime); // 사용자 및 토큰 삭제
        logger.info("Expired users and associated tokens have been deleted. : {} ", cutoffDateTime);
    }
}