package likelion.holymoly.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private String memberId;
    private String name;
    private String email;
    private String profileImage;
}
