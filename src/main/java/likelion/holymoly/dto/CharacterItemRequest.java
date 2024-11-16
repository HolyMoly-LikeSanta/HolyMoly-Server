package likelion.holymoly.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterItemRequest {
    private Long bgId;
    private Long headId;
    private Long faceId;
    private Long clothesId;
    private Long accessoryId;
}
