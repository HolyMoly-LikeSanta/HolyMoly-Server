package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import likelion.holymoly.dto.BoardDto;
import likelion.holymoly.dto.LetterDto;
import likelion.holymoly.entity.Board;
import likelion.holymoly.entity.Letter;
import likelion.holymoly.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/board")
@Tag(name = "게시판 관리", description = "게시판 및 편지 관리 API")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(summary = "게시판 조회", description = "boardId에 해당하는 게시판의 정보를 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long boardId) {
        Board board = boardService.getBoardById(boardId);
        BoardDto boardDto = new BoardDto(board.getMember().getId());
        return ResponseEntity.ok(boardDto);
    }

    @Operation(summary = "게시판 편지 리스트 조회", description = "boardId에 해당하는 게시판에 게시된 모든 편지들을 조회합니다.")
    @GetMapping("/{boardId}/letter")
    public ResponseEntity<List<LetterDto>> getAllLettersByBoardId(@PathVariable Long boardId) {
        List<Letter> letters = boardService.getAllLettersByBoardId(boardId);
        List<LetterDto> letterDtos = letters.stream()
                .map(letter -> new LetterDto(letter.getBoard().getBoardId(), letter.getContent(), letter.getAuthorNickname()))
                .toList();
        return ResponseEntity.ok(letterDtos);
    }

    @Operation(summary = "초대장 생성", description = "초대장을 생성하고 그 내용을 바탕으로 게시판을 생성합니다.")
    @PostMapping("/invite")
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto boardDto) {
        Board createdBoard = boardService.createBoard(boardDto);
        return ResponseEntity.ok(createdBoard);
    }

    @Operation(summary = "편지 작성", description = "boardId에 해당하는 게시판에 편지를 게시합니다.")
    @PostMapping("/{boardId}/letter")
    public ResponseEntity<Letter> createLetter(@PathVariable Long boardId, @RequestBody LetterDto letterDto) {
        Letter createdLetter = boardService.createLetter(boardId, letterDto);
        return ResponseEntity.ok(createdLetter);
    }

    @Operation(summary = "편지 삭제", description = "보드 ID와 편지 ID로 보드에서 편지를 삭제합니다.")
    @DeleteMapping("/{boardId}/letter/{letterId}")
    public ResponseEntity<?> deleteLetter(@PathVariable Long boardId, @PathVariable Long letterId) {
        try {
            boardService.deleteLetter(boardId, letterId);
            return ResponseEntity.ok(Collections.singletonMap("message", "편지가 성공적으로 삭제되었습니다."));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "해당 편지를 찾을 수 없습니다."));
        }
    }
}
