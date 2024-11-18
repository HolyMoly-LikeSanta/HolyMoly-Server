package likelion.holymoly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accessory_id")
    private Long accessoryId;

    @Column(name = "accessory_name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private String color;

    @Builder
    public Accessory(String name, String imageUrl, String color) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.color = color;
    }
}
