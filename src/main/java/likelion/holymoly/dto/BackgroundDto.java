package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import likelion.holymoly.entity.Background;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundDto {
    private Long bgId;
    private String name;
    private String imageUrl;
    private String description;

    // Entity -> DTO 변환을 위한 메서드
    public static BackgroundDto fromEntity(Background background) {
        return new BackgroundDto(
                background.getBgId(),
                background.getName(),
                background.getImageUrl(),
                background.getColor()
        );
    }
}
