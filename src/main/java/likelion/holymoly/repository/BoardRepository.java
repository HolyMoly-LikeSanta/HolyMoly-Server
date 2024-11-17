package likelion.holymoly.repository;

import likelion.holymoly.entity.Board;
import likelion.holymoly.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * Find a Board by the associated Member entity.
     * @param member Member entity
     * @return Optional<Board>
     */
    Optional<Board> findByMember(Member member);

    /**
     * Find a Board by the Member's ID.
     * @param memberId ID of the Member
     * @return Optional<Board>
     */
    Optional<Board> findByMember_Id(Long memberId);
}
