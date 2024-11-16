package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Head {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "head_id")
    private Long headId;

    @Column(name = "head_name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private String color;

    @Builder
    public Head(String name, String imageUrl, String color) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
