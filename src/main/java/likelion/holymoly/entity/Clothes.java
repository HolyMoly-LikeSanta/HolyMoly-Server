package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clothes_id")
    private Long clothesId;

    @Column(name = "clothes_name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private String color;

    @Builder
    public Clothes(String name, String imageUrl, String color) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
