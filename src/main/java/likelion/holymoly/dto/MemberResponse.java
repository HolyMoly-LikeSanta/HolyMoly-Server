package likelion.holymoly.dto;

import likelion.holymoly.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private Long memberId;
    private String name;
    private String kakaoId;
    private String profileImage;

    public static MemberResponse getMemberResponse(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .kakaoId(member.getKakaoId())
                .profileImage(member.getProfileImage())
                .build();
    }
}
