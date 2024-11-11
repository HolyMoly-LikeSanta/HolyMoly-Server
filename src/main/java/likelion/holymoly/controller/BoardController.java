package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/board")
@Tag(name = "게시판 관리", description = "게시판 및 편지 관리 API")
public class BoardController {

    @Operation(summary = "게시판 조회", description = "boardId에 해당하는 게시판의 정보를 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<String> getBoard(@Parameter(description = "게시판 ID") @PathVariable Long boardId) {
        // 추후 입력
        return ResponseEntity.ok("게시판 정보 조회 성공: ID " + boardId);
    }

    @Operation(summary = "게시판 편지 리스트 조회", description = "boardId에 해당하는 게시판에 게시된 모든 편지들을 조회합니다.")
    @GetMapping("/{boardId}/letter")
    public ResponseEntity<String> getLettersInBoard(@Parameter(description = "게시판 ID") @PathVariable Long boardId) {
        // 추후 입력
        return ResponseEntity.ok("게시판 내 편지 리스트 조회 성공: ID " + boardId);
    }

    @Operation(summary = "초대장 생성", description = "초대장을 생성하고 그 내용을 바탕으로 게시판을 생성합니다.")
    @PostMapping("/invite")
    public ResponseEntity<String> createBoardInvite() {
        // 추후 입력
        return ResponseEntity.ok("초대장 생성 성공");
    }

    @Operation(summary = "편지 작성", description = "boardId에 해당하는 게시판에 편지를 게시합니다.")
    @PostMapping("/{boardId}/letter")
    public ResponseEntity<String> createLetterInBoard(@Parameter(description = "게시판 ID") @PathVariable Long boardId) {
        // 추후 입력
        return ResponseEntity.ok("게시판 내 편지 작성 성공: ID " + boardId);
    }
}
