package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import likelion.holymoly.entity.Face;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FaceDto {
    private Long faceId;
    private String name;
    private String imageUrl;
    private String description;

    public static FaceDto fromEntity(Face face) {
        return new FaceDto(
                face.getFaceId(),
                face.getName(),
                face.getImageUrl(),
                face.getDescription()
        );
    }
}
