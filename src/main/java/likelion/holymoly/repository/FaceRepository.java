package likelion.holymoly.repository;

import likelion.holymoly.entity.Face;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceRepository extends JpaRepository<Face, Long> {
}
