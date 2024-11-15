package likelion.holymoly.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String grantType;
    private String accessToken;
}
