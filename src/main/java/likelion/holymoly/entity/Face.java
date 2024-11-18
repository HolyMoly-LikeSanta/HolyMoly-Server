package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Face {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "face_id")
    private Long faceId;

    @Column(name = "face_name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private String color;

    @Builder
    public Face(String name, String imageUrl, String color) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
