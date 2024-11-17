package likelion.holymoly.service;

import jakarta.transaction.Transactional;
import likelion.holymoly.dto.BoardDto;
import likelion.holymoly.dto.LetterDto;
import likelion.holymoly.entity.Board;
import likelion.holymoly.entity.Letter;
import likelion.holymoly.entity.Member;
import likelion.holymoly.exception.CustomException;
import likelion.holymoly.exception.ErrorCode;
import likelion.holymoly.repository.BoardRepository;
import likelion.holymoly.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LetterRepository letterRepository;

    /**
     * 멤버가 이미 보유한 보드가 없을 경우 생성
     */
    public Board createBoardIfNotExists(Member member) {
        return boardRepository.findByMember(member)
                .orElseGet(() -> {
                    Board board = Board.builder()
                            .member(member)
                            .build();
                    return boardRepository.save(board);
                });
    }

    public Letter createLetterByUserId(Long userId, LetterDto letterDto) {
        // userId를 기반으로 Board 조회
        Board board = boardRepository.findByMember_Id(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)); // 보드가 없을 경우 예외 처리

        // 편지 내용 검증
        if (letterDto.getContent() == null || letterDto.getContent().isBlank()) {
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND); // 내용이 비어있을 경우 예외 처리
        }

        // 편지 생성 및 저장
        Letter letter = Letter.builder()
                .board(board)
                .content(letterDto.getContent())
                .authorNickname(letterDto.getAuthorNickname())
                .build();

        return letterRepository.save(letter);
    }


    @Transactional
    public void deleteLetterByUserId(Long userId, Long letterId) {
        // userId를 기반으로 Board 조회
        Board board = boardRepository.findByMember_Id(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)); // 보드가 없을 경우 예외

        // Letter 조회
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND)); // 편지가 없을 경우 예외

        // Letter가 해당 Board에 속해 있는지 검증
        if (!letter.getBoard().equals(board)) {
            throw new CustomException(ErrorCode.LETTER_NOT_BELONG_TO_BOARD); // 편지가 해당 보드에 속하지 않을 경우 예외
        }

        // 삭제
        letterRepository.delete(letter);
    }


    /**
     * 사용자 ID를 기반으로 편지 리스트 조회
     */
    public List<LetterDto> getLettersByUserId(Long userId) {
        Board board = boardRepository.findByMember_Id(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)); // 보드가 없을 경우 예외

        return board.getLetters().stream()
                .map(letter -> new LetterDto(
                        letter.getContent(),
                        letter.getAuthorNickname()))
                .toList();
    }
}
