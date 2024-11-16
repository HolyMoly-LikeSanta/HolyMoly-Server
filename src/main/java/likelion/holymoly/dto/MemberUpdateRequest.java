package likelion.holymoly.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {
    private String name;
    private String profileImage;
}
