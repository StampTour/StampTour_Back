package com.stamptour.stampback.service;

import com.stamptour.stampback.domain.User;
import com.stamptour.stampback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

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

    //24시간이 지난 user 정보 삭제
    @Scheduled(cron = "0 0 * * * *") // 매일 자정에 실행
    public void deleteExpiredUsers() {
        LocalDateTime expirationTime = LocalDateTime.now().minusDays(1);
        List<User> expiredUsers = userRepository.findByCreatedAtBefore(expirationTime);
        userRepository.deleteAll(expiredUsers);
    }

}
