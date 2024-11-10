package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Invitation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long invitationId;

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
    public Invitation(Mission mission, Member member, String nickname, ColorTheme colorTheme) {
        this.mission = mission;
        this.member = member;
        this.nickname = nickname;
        this.colorTheme = colorTheme;
    }
}
