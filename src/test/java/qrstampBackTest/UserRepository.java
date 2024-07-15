package qrstampBack;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // 닉네임으로 사용자 조회 메서드 추가
    User findByUserid(String userid);
}
