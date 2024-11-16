package likelion.holymoly.service;

import jakarta.transaction.Transactional;
import likelion.holymoly.dto.BoardDto;
import likelion.holymoly.dto.LetterDto;
import likelion.holymoly.entity.Board;
import likelion.holymoly.entity.Letter;
import likelion.holymoly.entity.Member;
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

    public Board createBoard(BoardDto boardDto) {
        Member member = memberRepository.findById(boardDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Board board = Board.builder()
                .member(member)
                .build();

        return boardRepository.save(board);
    }

    public Letter createLetter(Long boardId, LetterDto letterDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        Letter letter = Letter.builder()
                .board(board)
                .content(letterDto.getContent())
                .authorNickname(letterDto.getAuthorNickname())
                .build();

        return letterRepository.save(letter);
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
    }

    public List<Letter> getAllLettersByBoardId(Long boardId) {
        Board board = getBoardById(boardId);
        return letterRepository.findByBoard(board);
    }

    @Transactional
    public void deleteLetter(Long boardId, Long letterId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new IllegalArgumentException("Letter not found"));

        if (!letter.getBoard().equals(board)) {
            throw new IllegalArgumentException("Letter does not belong to the specified board");
        }

        letterRepository.delete(letter);
    }
}
