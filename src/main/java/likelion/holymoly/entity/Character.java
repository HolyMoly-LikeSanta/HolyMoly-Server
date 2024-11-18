package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "character_table")
@NoArgsConstructor
@AllArgsConstructor
public class Character extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "char_id")
    private Long charId;

    private Long backgroundId;

    private Long headId;

    private Long faceId;

    private Long clothesId;

    private Long accessoryId;

    @Builder
    public Character(Long backgroundId, Long headId, Long faceId, Long clothesId, Long accessoryId) {
        this.backgroundId = backgroundId;
        this.headId = headId;
        this.faceId = faceId;
        this.clothesId = clothesId;
        this.accessoryId = accessoryId;
    }
}
