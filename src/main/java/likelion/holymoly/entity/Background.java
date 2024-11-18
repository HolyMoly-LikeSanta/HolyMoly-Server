package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bg_id")
    private Long bgId;

    @Column(name = "background_name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private String color;

    @Builder
    public Background(String name, String imageUrl, String color) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
