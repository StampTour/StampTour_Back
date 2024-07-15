package qrstampBack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> registerData) {
        String userid = registerData.get("userid");

        User user = new User();
        user.setUserid(userid);
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
