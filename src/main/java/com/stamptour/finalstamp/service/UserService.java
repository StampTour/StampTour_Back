package com.stamptour.finalstamp.service;

import com.stamptour.finalstamp.controller.StampController;
import com.stamptour.finalstamp.domain.User;
import com.stamptour.finalstamp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        userRepository.save(user);
    }

    // 토큰 저장 메서드 추가
    public void saveUserToken(User user, String token) {
        user.setToken(token);
        userRepository.save(user);
    }

    // findByToken 메서드 추가
    public User findByToken(String token) {
        return userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("User not found for token: " + token));
    }

    // 1분마다 실행되도록 크론 표현식 수정
    @Scheduled(cron = "0 0 * * * *") // 매 시마다 실행
    public void deleteExpiredUsers() {
        LocalDateTime expirationTime = LocalDateTime.now().minusDays(1); // 1분 전의 시간
        List<User> expiredUsers = userRepository.findByCreatedAtBefore(expirationTime);
        userRepository.deleteAll(expiredUsers);

        // 로그를 추가하여 어떤 사용자가 삭제되었는지 확인
        logger.info("Deleted users who were created before: " + expirationTime);
    }

}
