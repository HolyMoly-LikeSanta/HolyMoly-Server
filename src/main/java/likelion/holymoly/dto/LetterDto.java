package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterDto {
    private Long letterId;         // 편지 ID
    private String content;        // 편지 내용
    private String authorNickname; // 작성자 닉네임
}
