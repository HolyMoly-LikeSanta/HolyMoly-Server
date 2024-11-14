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
                .bgId(character.getBackground() != null ? character.getBackground().getBgId() : null)
                .headId(character.getHead() != null ? character.getHead().getHeadId() : null)
                .faceId(character.getFace() != null ? character.getFace().getFaceId() : null)
                .clothesId(character.getClothes() != null ? character.getClothes().getClothesId() : null)
                .accessoryId(character.getAccessory() != null ? character.getAccessory().getAccessoryId() : null)
                .build();
    }
}
