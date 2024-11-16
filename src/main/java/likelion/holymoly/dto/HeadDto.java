package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import likelion.holymoly.entity.Head;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeadDto {
    private Long headId;
    private String name;
    private String imageUrl;
    private String color;

    // Entity -> DTO 변환 메서드
    public static HeadDto fromEntity(Head head) {
        return new HeadDto(
                head.getHeadId(),
                head.getName(),
                head.getImageUrl(),
                head.getColor()
        );
    }
}
