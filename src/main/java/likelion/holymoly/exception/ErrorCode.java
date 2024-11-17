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
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"db-001", "DB에서 유저를 찾을 수 없습니다."),
    CHARACTER_NOT_FOUND(HttpStatus.NOT_FOUND, "db-002", "DB에서 해당 유저의 캐릭터를 찾을 수 없습니다."),
    BACKGROUND_NOT_FOUND(HttpStatus.NOT_FOUND, "db-003", "DB에서 Background를 찾을 수 없습니다."),
    HEAD_NOT_FOUND(HttpStatus.NOT_FOUND, "db-003", "DB에서 Head를 찾을 수 없습니다."),
    FACE_NOT_FOUND(HttpStatus.NOT_FOUND, "db-003", "DB에서 Face를 찾을 수 없습니다."),
    CLOTHES_NOT_FOUND(HttpStatus.NOT_FOUND, "db-003", "DB에서 Clothes를 찾을 수 없습니다."),
    ACCESSORY_NOT_FOUND(HttpStatus.NOT_FOUND, "db-003", "DB에서 Accessory를 찾을 수 없습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "db-004", "DB에서 게시판을 찾을 수 없습니다."),
    CONTENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "db-005", "편지 내용이 비어 있습니다."),
    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "db-006", "DB에서 편지를 찾을 수 없습니다."),
    LETTER_NOT_BELONG_TO_BOARD(HttpStatus.BAD_REQUEST, "db-007", "편지가 지정된 게시판에 속하지 않습니다."),
    BOARD_ALREADY_EXISTS(HttpStatus.CONFLICT, "db-008", "이미 게시판이 존재합니다."),


    DUMMY_ERROR_CODE(HttpStatus.OK, "DUMMY", "DUMMY");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
