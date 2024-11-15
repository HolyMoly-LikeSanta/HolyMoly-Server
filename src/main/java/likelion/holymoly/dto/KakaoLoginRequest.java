package likelion.holymoly.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoLoginRequest {
    @NotNull
    private String code;
}
