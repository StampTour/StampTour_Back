package qrstampBack;

import qrstampBack.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User login(String userid) {
        return userRepository.findById(userid).orElseGet(() -> {
            User newUser = new User();
            newUser.setUserid(userid);
            return userRepository.save(newUser);
        });
    }

    public User getUser(String userid) {
        return userRepository.findById(userid).orElse(null);
    }

    public User addStamp(String userid, int stampIndex) {
        User user = userRepository.findById(userid).orElseThrow();
        user.getStamps()[stampIndex] = true;
        return userRepository.save(user);
    }

    public void deleteSessionDataFromDatabase(String sessionId) {
        // 세션 데이터 삭제 로직 구현
        // userRepository.deleteBy... 등의 메서드를 사용하여 데이터 삭제
    }
}
