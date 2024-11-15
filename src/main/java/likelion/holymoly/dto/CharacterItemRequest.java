package likelion.holymoly.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterItemRequest {
    private Long bgId;
    private Long headId;
    private Long faceId;
    private Long clothesId;
    private Long accessoryId;
}
