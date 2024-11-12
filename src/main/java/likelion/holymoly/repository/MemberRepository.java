package likelion.holymoly.repository;

import likelion.holymoly.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원 조회하기 위한 메서드 예시
    Optional<Member> findByEmail(String email);
}
