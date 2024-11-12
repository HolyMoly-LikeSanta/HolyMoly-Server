package likelion.holymoly.service;

import likelion.holymoly.dto.BoardDto;
import likelion.holymoly.dto.LetterDto;
import likelion.holymoly.entity.Board;
import likelion.holymoly.entity.Letter;
import likelion.holymoly.entity.Member;
import likelion.holymoly.entity.Mission;
import likelion.holymoly.repository.BoardRepository;
import likelion.holymoly.repository.LetterRepository;
import likelion.holymoly.repository.MemberRepository;
import likelion.holymoly.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LetterRepository letterRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;

    public Board createBoard(BoardDto boardDto) {
        Mission mission = missionRepository.findById(boardDto.getMissionId())
                .orElseThrow(() -> new IllegalArgumentException("Mission not found"));
        Member member = memberRepository.findById(boardDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Board board = Board.builder()
                .mission(mission)
                .member(member)
                .nickname(boardDto.getNickname())
                .colorTheme(boardDto.getColorTheme())
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
}
