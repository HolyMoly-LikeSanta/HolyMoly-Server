package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Letter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long letterId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String content;

    @Column(name = "author_nickname", length = 10, nullable = false)
    private String authorNickname;

    @Builder
    public Letter(Member member, String content, String authorNickname) {
        this.member = member;
        this.content = content;
        this.authorNickname = authorNickname;
    }
}
