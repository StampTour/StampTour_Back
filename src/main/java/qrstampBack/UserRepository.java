package qrstampBack;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserid(String userid);
    void deleteSessionDataFromDatabase();
}
