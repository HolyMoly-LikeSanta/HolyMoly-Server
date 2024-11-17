package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import likelion.holymoly.dto.BoardDto;
import likelion.holymoly.dto.LetterDto;
import likelion.holymoly.entity.Board;
import likelion.holymoly.entity.Letter;
import likelion.holymoly.entity.Member;
import likelion.holymoly.service.BoardService;
import likelion.holymoly.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/board")
@Tag(name = "게시판 관리", description = "게시판 및 편지 관리 API")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    public BoardController(BoardService boardService, MemberService memberService) {
        this.boardService = boardService;
        this.memberService = memberService;
    }

    @Operation(summary = "게시판의 편지 리스트 조회", description = "userId에 해당하는 게시판의 편지 리스트를 조회합니다.")
    @GetMapping("/{userId}/letters")
    public ResponseEntity<List<LetterDto>> getLettersByUserId(@PathVariable Long userId) {
        List<LetterDto> letterDtos = boardService.getLettersByUserId(userId); // userId를 기반으로 편지 리스트 조회
        return ResponseEntity.ok(letterDtos);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "게시판 생성", description = "현재 사용자가 소유한 게시판이 없으면 새로 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<Board> createBoard(@AuthenticationPrincipal UserDetails userDetails) {
        // 현재 인증된 사용자 정보로 멤버 가져오기
        Member myMember = memberService.getMemberByPrincipal(userDetails);

        // BoardService에서 이미 존재 여부를 확인하고 처리
        Board createdBoard = boardService.createBoardIfNotExists(myMember);

        return ResponseEntity.ok(createdBoard);
    }


    @Operation(summary = "편지 작성", description = "userId에 해당하는 게시판에 편지를 게시합니다.")
    @PostMapping("/{userId}/letter")
    public ResponseEntity<Letter> createLetter(@PathVariable Long userId, @RequestBody LetterDto letterDto) {
        // userId를 사용하여 편지 생성
        Letter createdLetter = boardService.createLetterByUserId(userId, letterDto);
        return ResponseEntity.ok(createdLetter);
    }


    @Operation(summary = "편지 삭제", description = "userId와 편지 ID를 기반으로 편지를 삭제합니다.")
    @DeleteMapping("/{userId}/letter/{letterId}")
    public ResponseEntity<?> deleteLetter(@PathVariable Long userId, @PathVariable Long letterId) {
        try {
            boardService.deleteLetterByUserId(userId, letterId);
            return ResponseEntity.ok(Collections.singletonMap("message", "편지가 성공적으로 삭제되었습니다."));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "해당 편지를 찾을 수 없습니다."));
        }
    }

}
