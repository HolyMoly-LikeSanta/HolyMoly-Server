package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.holymoly.dto.LetterDto;
import likelion.holymoly.entity.Letter;
import likelion.holymoly.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/board")
@Tag(name = "멤버 관리", description = "멤버 및 편지 관리 API")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(summary = "멤버의 편지 리스트 조회", description = "memberId에 해당하는 멤버의 편지 리스트를 조회합니다.")
    @GetMapping("/{memberId}/letters")
    public ResponseEntity<List<LetterDto>> getLettersByMemberId(@PathVariable Long memberId) {
        // memberId를 기반으로 편지 리스트 조회
        List<LetterDto> letterDtos = boardService.getLettersByMemberId(memberId);
        return ResponseEntity.ok(letterDtos);
    }

    @Operation(summary = "편지 작성", description = "memberId에 해당하는 멤버에게 편지를 작성합니다.")
    @PostMapping("/{memberId}/letter")
    public ResponseEntity<LetterDto> createLetter(@PathVariable Long memberId, @RequestBody LetterDto letterDto) {
        // memberId를 기반으로 편지 생성 후 DTO 반환
        LetterDto createdLetter = boardService.createLetterByMemberId(memberId, letterDto);
        return ResponseEntity.ok(createdLetter);
    }

    @Operation(summary = "편지 삭제", description = "memberId와 편지 ID를 기반으로 편지를 삭제합니다.")
    @DeleteMapping("/{memberId}/letter/{letterId}")
    public ResponseEntity<?> deleteLetter(@PathVariable Long memberId, @PathVariable Long letterId) {
        try {
            // memberId와 letterId를 기반으로 편지 삭제
            boardService.deleteLetterByMemberId(memberId, letterId);
            return ResponseEntity.ok(Collections.singletonMap("message", "편지가 성공적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "해당 편지를 찾을 수 없습니다."));
        }
    }
}
