package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long boardId;
    private Long memberId;
    private String name;
    private List<LetterDto> letters;
}