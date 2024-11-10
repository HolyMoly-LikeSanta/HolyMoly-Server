package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "color_theme", nullable = false)
    private ColorTheme colorTheme;

    @Builder
    public Board(Mission mission, Member member, String nickname, ColorTheme colorTheme) {
        this.mission = mission;
        this.member = member;
        this.nickname = nickname;
        this.colorTheme = colorTheme;
    }
}
