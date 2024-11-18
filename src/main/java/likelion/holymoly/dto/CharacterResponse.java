package likelion.holymoly.dto;

import likelion.holymoly.entity.Character;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterResponse {
    private Long charId;
    private Long bgId;
    private Long headId;
    private Long faceId;
    private Long clothesId;
    private Long accessoryId;

    public static CharacterResponse getCharacterResponse(Character character) {
        return CharacterResponse.builder()
                .charId(character.getCharId())
                .bgId(character.getBackgroundId() != null ? character.getBackgroundId() : null)
                .headId(character.getHeadId() != null ? character.getHeadId() : null)
                .faceId(character.getFaceId() != null ? character.getFaceId() : null)
                .clothesId(character.getClothesId() != null ? character.getClothesId() : null)
                .accessoryId(character.getAccessoryId() != null ? character.getAccessoryId() : null)
                .build();
    }
}
