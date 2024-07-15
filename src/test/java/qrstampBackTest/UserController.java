package qrstampBack;//package qrstampBack;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import jakarta.servlet.http.HttpSession;
//
//
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/save-userid")
//    public ResponseEntity<String> saveUserid(@RequestBody String userid) {
//        userService.saveUseridToSessionAndDatabase(userid);
//        return ResponseEntity.status(HttpStatus.CREATED).body("userid saved successfully");
//    }
//
//    @GetMapping("/check-session")
//    public ResponseEntity<String> checkSession() {
//        String userid = (String) httpSession.getAttribute("userid");
//        if (userid != null) {
//            return ResponseEntity.ok("User with nickname " + userid + " is logged in.");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
//        }
//    }
//
//    @DeleteMapping("/logout")
//    public ResponseEntity<String> logout() {
//        String userId = (Long) httpSession.getAttribute("userId");
//        if (userId != null) {
//            userService.deleteExpiredSessionAndDbData(userId);
//            return ResponseEntity.ok("Logout successful.");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
//        }
//    }
//}
//
