package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import likelion.holymoly.entity.ColorTheme;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long missionId;
    private Long memberId;
    private String nickname;
    private ColorTheme colorTheme;
}