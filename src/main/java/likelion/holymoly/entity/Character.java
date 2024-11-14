package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Character extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "char_id")
    private Long charId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bg_id", nullable = false)
    private Background background;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "head_id")
    private Head head;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "face_id")
    private Face face;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accessory_id")
    private Accessory accessory;

    @Builder
    public Character(Background background, Head head, Face face, Clothes clothes, Accessory accessory) {
        this.background = background;
        this.head = head;
        this.face = face;
        this.clothes = clothes;
        this.accessory = accessory;
    }
}
