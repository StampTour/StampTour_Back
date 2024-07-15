package qrstampBack;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Autowired
    public UserService(UserRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }

    public void saveUseridToSessionAndDatabase(String userid) {
        // 세션에 닉네임 저장
        httpSession.setAttribute("userid", userid);

        // 데이터베이스에 저장
        User user = new User();
        user.setUserid(userid);
        userRepository.save(user);
    }

    public User findUserByUserid(String userid) {
        return userRepository.findByUserid(userid);
    }

    public void deleteExpiredSessionAndDbData(String userId) {
        // 세션에서 닉네임 삭제
        httpSession.removeAttribute("userid");

        // 데이터베이스에서 삭제
        userRepository.deleteById(userId);
    }
}