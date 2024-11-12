package likelion.holymoly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // OAuth
    KAKAO_FETCH_ACCESS_TOKEN_FAIL(HttpStatus.UNAUTHORIZED, "oauth-001", "카카오 엑세스 토큰을 획득하는 데에 실패하였습니다."),
    KAKAO_FETCH_USER_DATA_FAIL(HttpStatus.UNAUTHORIZED, "oauth-002", "카카오 유저 정보를 획득하는 데에 실패하였습니다."),

    // DB
    USER_NOT_FOUND_IN_DB(HttpStatus.NOT_FOUND,"db-001", "DB에서 유저를 찾을 수 없습니다."),

    DUMMY_ERROR_CODE(HttpStatus.OK, "DUMMY", "DUMMY");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
