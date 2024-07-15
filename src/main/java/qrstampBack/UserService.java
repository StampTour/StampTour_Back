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

}
