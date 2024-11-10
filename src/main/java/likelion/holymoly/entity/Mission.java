package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mission extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long missionId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int sequence;

    @Builder
    public Mission(String name, String description, int sequence) {
        this.name = name;
        this.description = description;
        this.sequence = sequence;
    }
}
