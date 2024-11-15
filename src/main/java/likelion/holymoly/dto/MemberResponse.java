package likelion.holymoly.dto;

import likelion.holymoly.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private Long memberId;
    private String name;
    private String email;
    private String profileImage;

    public static MemberResponse getMemberResponse(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .build();
    }
}
