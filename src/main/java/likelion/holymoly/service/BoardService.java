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
import likelion.holymoly.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LetterRepository letterRepository;
    private final MemberRepository memberRepository;

    public Board createBoard(BoardDto boardDto, Member member) {

        if (member == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND); // 멤버가 없을 경우 예외 처리
        }

        Board board = Board.builder()
                .member(member)
                .build();

        return boardRepository.save(board);
    }

    public Letter createLetter(Long boardId, LetterDto letterDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)); // 게시판이 없을 경우 예외 처리

        if (letterDto.getContent() == null || letterDto.getContent().isBlank()) {
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND); // 편지 내용이 비어있는 경우 예외 처리
        }

        Letter letter = Letter.builder()
                .board(board)
                .content(letterDto.getContent())
                .authorNickname(letterDto.getAuthorNickname())
                .build();

        return letterRepository.save(letter);
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)); // 게시판이 없을 경우 예외 처리
    }

    public List<Letter> getAllLettersByBoardId(Long boardId) {
        Board board = getBoardById(boardId);
        return letterRepository.findByBoard(board);
    }

    @Transactional
    public void deleteLetter(Long boardId, Long letterId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)); // 게시판이 없을 경우 예외 처리

        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND)); // 편지가 없을 경우 예외 처리

        if (!letter.getBoard().equals(board)) {
            throw new CustomException(ErrorCode.LETTER_NOT_BELONG_TO_BOARD); // 편지가 지정된 게시판에 속하지 않을 경우 예외 처리
        }

        letterRepository.delete(letter);
    }
}
